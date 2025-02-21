package uc.util;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
public class ReentrantLockArrayList<E> extends ArrayList<E> {
	private static final long serialVersionUID = -6881913451159539758L;
	public ReentrantLockArrayList() {
		super();
	}
	public ReentrantLockArrayList(int initialCapacity) {
		super(initialCapacity);
	}
	public final ReentrantLockArrayList<E> shared() {
		return new Shared<E>(this);
	}
	private static final class Shared<E> extends ReentrantLockArrayList<E> {
		private static final long serialVersionUID = 5903643726292984434L;
		private final ReentrantLockArrayList<E> list;
		private final ReentrantLock rwLock;
		private Shared(ReentrantLockArrayList<E> target) {
			list = target;
			rwLock = new ReentrantLock();
		}
		@Override
		public boolean add(E value) {
			final ReentrantLock lock = rwLock;
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
			final ReentrantLock lock = rwLock;
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
			final ReentrantLock lock = rwLock;
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
			final ReentrantLock lock = rwLock;
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
			final ReentrantLock lock = rwLock;
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
			final ReentrantLock lock = rwLock;
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
			final ReentrantLock lock = rwLock;
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
			final ReentrantLock lock = rwLock;
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
			final ReentrantLock lock = rwLock;
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