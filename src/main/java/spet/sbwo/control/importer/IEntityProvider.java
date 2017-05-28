package spet.sbwo.control.importer;

import spet.sbwo.data.base.BaseEntity;

@FunctionalInterface
public interface IEntityProvider<T extends BaseEntity> {

    T getEntity(String key);

}
