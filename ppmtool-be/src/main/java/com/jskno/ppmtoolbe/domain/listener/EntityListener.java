//@formatter:off
/**
 *  $$Id$$
 *       . * .
 *     * RRRR  *    Copyright (c) 2017 EUIPO: European Union Intellectual
 *   .   RR  R   .  Property Office (trade marks and designs)
 *   *   RRR     *
 *    .  RR RR  .   ALL RIGHTS RESERVED
 *     * . _ . *
 */
//@formatter:on
package com.jskno.ppmtoolbe.domain.listener;


import com.jskno.ppmtoolbe.domain.base.AbstractEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

public class EntityListener {

    @PrePersist
    public void onCreate(final AbstractEntity entity) {
        //final String currentUser = SecurityContextHolder.getContext().getAuthentication().
    //                                      getName();
        final String currentUser = "SYSTEM";
        final Date currentDate = new Date();
        entity.setCreatedByUser(currentUser);
        entity.setCreatedAt(currentDate);
    }

    @PreUpdate
    public void onUpdate(final AbstractEntity entity) {
        final String currentUser = "SYSTEM";
        final Date currentDate = new Date();
        entity.setUpdatedByUser(currentUser);
        entity.setUpdatedAt(currentDate);
    }
}
