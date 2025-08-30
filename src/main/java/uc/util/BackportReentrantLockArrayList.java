package uc.util;
import java.util.ArrayList;
import edu.emory.mathcs.backport.java.util.concurrent.locks.Lock;
import edu.emory.mathcs.backport.java.util.concurrent.locks.ReentrantLock;
public class BackportReentrantLockArrayList<E> extends ArrayList<E> {
	private static final long serialVersionUID = -6881913451159539758L;
	public BackportReentrantLockArrayList() {
		super();
	}
	public BackportReentrantLockArrayList(int initialCapacity) {
		super(initialCapacity);
	}
	public final BackportReentrantLockArrayList<E> shared() {
		return new Shared<E>(this);
	}
	private static final class Shared<E> extends BackportReentrantLockArrayList<E> {
		private static final long serialVersionUID = 5903643726292984434L;
		private final BackportReentrantLockArrayList<E> list;
		private final Lock rwLock;
		private Shared(BackportReentrantLockArrayList<E> target) {
			list = target;
			rwLock = new ReentrantLock();
		}
		@Override
		public boolean add(E value) {
			final Lock lock = rwLock;
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
			final Lock lock = rwLock;
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
			final Lock lock = rwLock;
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
			final Lock lock = rwLock;
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
			final Lock lock = rwLock;
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
			final Lock lock = rwLock;
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
			final Lock lock = rwLock;
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
			final Lock lock = rwLock;
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
			final Lock lock = rwLock;
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