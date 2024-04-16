package com.hixtrip.sample.domain.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author went
 * @date 2024/4/13
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder(toBuilder = true)
public class Inventory {
    /**
     * skuId
     */
    private String skuId;
    /**
     *  可售库存
     */
    private Long sellableQuantity;
    /**
     *  预占库存
     */
    private Long withholdingQuantity;
    /**
     *  占用库存
     */
    private Long occupiedQuantity;
}
