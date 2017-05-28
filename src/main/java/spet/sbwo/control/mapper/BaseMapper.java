package spet.sbwo.control.mapper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import spet.sbwo.control.channel.BaseChannel;
import spet.sbwo.data.DatabaseError;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.base.BaseEntity;

public abstract class BaseMapper<I extends BaseEntity, O extends BaseChannel> {
    private List<PersistentEntityOperation> ops;
    protected boolean returnNullOnNullId;
    protected IDatabaseExecutor executor;

    public BaseMapper(IDatabaseExecutor executor) {
        super();
        this.executor = executor;
        this.ops = new LinkedList<>();
    }

    public BaseMapper() {
        this.returnNullOnNullId = false;
    }

    public void setReturnNullOnNullId(boolean returnNullOnNullId) {
        this.returnNullOnNullId = returnNullOnNullId;
    }

    public O toExternal(I input) {
        if (input != null) {
            O result = this.newExternal(input);
            this.merge(result, input);
            return result;
        } else {
            return null;
        }
    }

    public List<I> toInternal(List<O> input) {
        List<I> list = new LinkedList<>();
        if (input != null) {
            for (O ex : input) {
                list.add(this.toInternal(ex));
            }
        }
        return list;
    }

    public List<O> toExternal(List<I> input) {
        List<O> list = new LinkedList<>();
        if (input != null) {
            for (I in : input) {
                list.add(this.toExternal(in));
            }
        }
        return list;
    }

    public I toInternal(O input) {
        if (input != null && (input.getId() != null || !this.returnNullOnNullId)) {
            I result = this.newInternal(input);
            ops.add(new PersistentEntityOperation(result, PersistentEntityOperationType.CREATE));
            this.merge(result, input);
            return result;
        } else {
            return null;
        }
    }

    public void merge(I internal, O external) {
        ops.add(new PersistentEntityOperation(internal, PersistentEntityOperationType.UPDATE));
        this.ifNotNull(external.getId(), internal::setId);
    }

    public void merge(O external, I internal) {
        external.setId(internal.getId());
    }

    public void flush() {
        for (PersistentEntityOperation op : ops) {
            op.execute(executor);
        }
        ops.clear();
    }

    protected void addOperations(int index, BaseMapper<?, ?> mapper) {
        ops.addAll(index, mapper.ops);
    }

    protected void addOperations(BaseMapper<?, ?> mapper) {
        ops.addAll(mapper.ops);
    }

    protected void addOperation(PersistentEntityOperation op) {
        ops.add(op);
    }

    protected void addOperation(int index, PersistentEntityOperation op) {
        ops.add(index, op);
    }

    protected abstract I newInternal(O external);

    protected abstract O newExternal(I internal);

    protected <T> void ifNotNull(T t, IDeffered<T> def) {
        if (t != null) {
            def.execute(t);
        }
    }

    protected <P, C> List<C> ifNotNullForEach(List<C> input, P p, IPairDeffered<P, C> def) {
        if (input != null) {
            for (C c : input) {
                def.execute(c, p);
            }
        }
        return input;
    }

    protected <T> T getEnumValue(T[] values, Integer index) {
        if (index == null) {
            return null;
        } else if (index >= 0 && index < values.length) {
            return values[index];
        } else {
            return null;
        }
    }

    protected List<I> merge(List<I> internal, List<O> external) {
        List<I> result = new LinkedList<>();

        if (internal != null && external != null) {
            Map<Integer, I> internalMap = new HashMap<>();
            for (I in : internal) {
                internalMap.put(in.getId(), in);
            }
            for (O ex : external) {
                I in = internalMap.get(ex.getId());
                if (in != null) {
                    this.merge(in, ex);
                    internalMap.remove(ex.getId());
                } else {
                    in = this.toInternal(ex);
                    ops.add(this.buildCreate(in));
                }
                result.add(in);
            }
            for (Map.Entry<Integer, I> entry : internalMap.entrySet()) {
                ops.add(this.buildDelete(entry.getValue()));
            }
        } else if (internal != null) {
            for (I in : internal) {
                ops.add(this.buildDelete(in));
            }
        } else if (external != null) {
            for (O ex : external) {
                I in = this.toInternal(ex);
                this.buildCreate(in);
                result.add(in);
            }
        }

        return result;
    }

    @FunctionalInterface
    protected static interface IDeffered<T> {
        void execute(T t);
    }

    @FunctionalInterface
    protected static interface IPairDeffered<P, C> {
        void execute(C c, P p);
    }

    protected PersistentEntityOperation buildDelete(BaseEntity entity) {
        return new PersistentEntityOperation(entity, PersistentEntityOperationType.DELETE);
    }

    protected PersistentEntityOperation buildCreate(BaseEntity entity) {
        return new PersistentEntityOperation(entity, PersistentEntityOperationType.CREATE);
    }

    protected PersistentEntityOperation buildUpdate(BaseEntity entity) {
        return new PersistentEntityOperation(entity, PersistentEntityOperationType.UPDATE);
    }

    protected enum PersistentEntityOperationType {
        CREATE, DELETE, UPDATE
    }

    protected static class PersistentEntityOperation {
        private BaseEntity entity;
        private PersistentEntityOperationType type;

        public PersistentEntityOperation(BaseEntity entity, PersistentEntityOperationType type) {
            super();
            this.entity = entity;
            this.type = type;
        }

        public void execute(IDatabaseExecutor executor) {
            switch (this.type) {
                case CREATE:
                    executor.create(this.entity);
                    break;
                case DELETE:
                    executor.delete(this.entity);
                    break;
                case UPDATE:
                    executor.update(this.entity);
                    break;
                default:
                    throw new DatabaseException(DatabaseError.OTHER, "Unknown database operation.");
            }
        }
    }
}
