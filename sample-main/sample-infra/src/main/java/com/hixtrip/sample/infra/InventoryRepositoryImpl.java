package com.hixtrip.sample.infra;

import cn.hutool.json.JSONObject;
import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * 库存key
     */
    public static final String INVENTORY_SELLABLE_KEY = "inventory:sellableQuantity:";
    /**
     * 预占库存key
     */
    public static final String INVENTORY_WITHHOLDING_KEY = "inventory:withholdingQuantity:";

    @Override
    public Integer getInventory(String skuId) {
        return  (Integer) Objects.requireNonNull(redisTemplate.opsForValue().get(INVENTORY_SELLABLE_KEY + skuId));
    }

    @Override
    public Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        if(StringUtils.isNotBlank(skuId) && ObjectUtils.allNotNull(sellableQuantity, withholdingQuantity, occupiedQuantity)){
            /**
             * lua脚本
             * 传入的参数分别为可售库存（修改数量），预占库存（修改数量），占用库存
             * 当缓存可售库存大于等于修改库存量时，减库存，增加预占库存
             */
            String script =
                    "if tonumber((redis.call('get',KEYS[1]))) >= tonumber(ARGV[1]) then" +
                    "   redis.call('decrby',KEYS[1],tonumber(ARGV[1])) " +
                    "   redis.call('incrby',KEYS[2],tonumber(ARGV[2])) " +
                    "   return 1; " +
                    "else " +
                    "   return 0;" +
                    "end;";
            DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
            redisScript.setScriptText(script);
            redisScript.setResultType(Long.class);
            List<String> keys = new ArrayList<>();
            keys.add(INVENTORY_SELLABLE_KEY + skuId);
            keys.add(INVENTORY_WITHHOLDING_KEY + skuId);
            //keys.add(INVENTORY_OCCUPIED_KEY + skuId);

            Long result = redisTemplate.execute(redisScript, keys, sellableQuantity,withholdingQuantity);
            if(ObjectUtils.isNotEmpty(result) && result == 1){
                ValueOperations<String, Object> ops = redisTemplate.opsForValue();
                System.out.println("库存扣减成功");
                System.out.println(ops.get(INVENTORY_SELLABLE_KEY + skuId));
                System.out.println(ops.get(INVENTORY_WITHHOLDING_KEY + skuId));
                return true;
            }
            System.out.println("库存扣减失败");
            return false;
        }
        return false;
    }

    @Override
    public void createInventory(Inventory inventory) {
        redisTemplate.opsForValue().set("inventory:" + inventory.getSkuId(), inventory);
        redisTemplate.opsForValue().set(INVENTORY_SELLABLE_KEY+ inventory.getSkuId(), inventory.getSellableQuantity());
        redisTemplate.opsForValue().set(INVENTORY_WITHHOLDING_KEY + inventory.getSkuId(), inventory.getWithholdingQuantity());
    }
}
