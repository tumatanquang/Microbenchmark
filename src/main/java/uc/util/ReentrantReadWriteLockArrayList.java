package uc.util;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class ReentrantReadWriteLockArrayList<E> extends ArrayList<E> {
	private static final long serialVersionUID = -6881913451159539758L;
	public ReentrantReadWriteLockArrayList() {
		super();
	}
	public ReentrantReadWriteLockArrayList(int initialCapacity) {
		super(initialCapacity);
	}
	public final ReentrantReadWriteLockArrayList<E> shared() {
		return new Shared<E>(this);
	}
	private static final class Shared<E> extends ReentrantReadWriteLockArrayList<E> {
		private static final long serialVersionUID = 5903643726292984434L;
		private final ReentrantReadWriteLockArrayList<E> list;
		private final ReadWriteLock rwLock;
		private final Lock readLock;
		private final Lock writeLock;
		private Shared(ReentrantReadWriteLockArrayList<E> target) {
			list = target;
			rwLock = new ReentrantReadWriteLock();
			readLock = rwLock.readLock();
			writeLock = rwLock.writeLock();
		}
		@Override
		public boolean add(E value) {
			final Lock lock = writeLock;
			lock.lock();
			try {
				return list.add(value);
			}
			finally {
				lock.unlock();
			}
		}
		@Override
		public void clear() {
			final Lock lock = writeLock;
			lock.lock();
			try {
				list.clear();
			}
			finally {
				lock.unlock();
			}
		}
		@Override
		public boolean contains(Object o) {
			final Lock lock = readLock;
			lock.lock();
			try {
				return list.contains(o);
			}
			finally {
				lock.unlock();
			}
		}
		@Override
		public E get(int index) {
			final Lock lock = readLock;
			lock.lock();
			try {
				return list.get(index);
			}
			finally {
				lock.unlock();
			}
		}
		@Override
		public int indexOf(Object o) {
			final Lock lock = readLock;
			lock.lock();
			try {
				return list.indexOf(o);
			}
			finally {
				lock.unlock();
			}
		}
		@Override
		public boolean isEmpty() {
			final Lock lock = readLock;
			lock.lock();
			try {
				return list.isEmpty();
			}
			finally {
				lock.unlock();
			}
		}
		@Override
		public E remove(int index) {
			final Lock lock = writeLock;
			lock.lock();
			try {
				return list.remove(index);
			}
			finally {
				lock.unlock();
			}
		}
		@Override
		public boolean remove(Object o) {
			final Lock lock = writeLock;
			lock.lock();
			try {
				return list.remove(o);
			}
			finally {
				lock.unlock();
			}
		}
		@Override
		public int size() {
			final Lock lock = readLock;
			lock.lock();
			try {
				return list.size();
			}
			finally {
				lock.unlock();
			}
		}
	}
}