package by.vanzoneway.newsservicecore.cache.concurrent;


import java.util.concurrent.locks.ReadWriteLock;

public class Guard {

    private final ReadWriteLock readWriteLock;

    private Guard(ReadWriteLock readWriteLock) {
        this.readWriteLock = readWriteLock;
    }


    public <T> T executeWrite(UnitOfWork<T> unitOfWork) {
        readWriteLock.writeLock().lock();
        try {
            return unitOfWork.execute();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }


    public <T> T executeRead(UnitOfWork<T> unitOfWork) {
        readWriteLock.readLock().lock();
        try {
            return unitOfWork.execute();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }


    public void executeWrite(VoidUnitOfWork unitOfWork) {
        readWriteLock.writeLock().lock();
        try {
            unitOfWork.execute();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @FunctionalInterface
    public interface UnitOfWork<T> {


        T execute();
    }


    @FunctionalInterface
    public interface VoidUnitOfWork {


        void execute();
    }


    public static Guard guardedBy(ReadWriteLock readWriteLock) {
        return new Guard(readWriteLock);
    }
}

