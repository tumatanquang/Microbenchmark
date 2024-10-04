package uc.util;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class ConcurrentArrayList<E> extends ArrayList<E> {
	private static final long serialVersionUID = -6881913451159539758L;
	public ConcurrentArrayList() {
		super();
	}
	public ConcurrentArrayList(int initialCapacity) {
		super(initialCapacity);
	}
	public final ConcurrentArrayList<E> shared() {
		return new Shared();
	}
	private final class Shared extends ConcurrentArrayList<E> {
		private static final long serialVersionUID = 5903643726292984434L;
		private final ReadWriteLock lock;
		private Shared() {
			lock = new ReentrantReadWriteLock();
		}
		@Override
		public boolean add(E value) {
			lock.writeLock().lock();
			try {
				return super.add(value);
			}
			finally {
				lock.writeLock().unlock();
			}
		}
		@Override
		public void clear() {
			lock.writeLock().lock();
			try {
				super.clear();
			}
			finally {
				lock.writeLock().unlock();
			}
		}
		@Override
		public boolean contains(Object o) {
			lock.readLock().lock();
			try {
				return super.contains(o);
			}
			finally {
				lock.readLock().unlock();
			}
		}
		@Override
		public E get(int index) {
			lock.readLock().lock();
			try {
				return super.get(index);
			}
			finally {
				lock.readLock().unlock();
			}
		}
		@Override
		public int indexOf(Object o) {
			lock.readLock().lock();
			try {
				return super.indexOf(o);
			}
			finally {
				lock.readLock().unlock();
			}
		}
		@Override
		public boolean isEmpty() {
			lock.readLock().lock();
			try {
				return super.isEmpty();
			}
			finally {
				lock.readLock().unlock();
			}
		}
		@Override
		public E remove(int index) {
			lock.writeLock().lock();
			try {
				return super.remove(index);
			}
			finally {
				lock.writeLock().unlock();
			}
		}
		@Override
		public boolean remove(Object o) {
			lock.writeLock().lock();
			try {
				return super.remove(o);
			}
			finally {
				lock.writeLock().unlock();
			}
		}
		@Override
		public int size() {
			lock.readLock().lock();
			try {
				return super.size();
			}
			finally {
				lock.readLock().unlock();
			}
		}
	}
}