package spet.sbwo.control.importer.base;

import spet.sbwo.data.base.BaseEntity;

@FunctionalInterface
public interface IEntityProvider<T extends BaseEntity> {

    T getEntity(String key);

}
