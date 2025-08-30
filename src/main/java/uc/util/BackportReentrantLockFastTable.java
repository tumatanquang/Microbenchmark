package uc.util;
import edu.emory.mathcs.backport.java.util.concurrent.locks.Lock;
import edu.emory.mathcs.backport.java.util.concurrent.locks.ReentrantLock;
import javolution.util.FastTable;
public class BackportReentrantLockFastTable<E> extends FastTable<E> {
	private static final long serialVersionUID = 2781771606854788734L;
	public BackportReentrantLockFastTable() {
		super();
	}
	public BackportReentrantLockFastTable(int capacity) {
		super(capacity);
	}
	@Override
	public final BackportReentrantLockFastTable<E> shared() {
		return new Shared<E>(this);
	}
	private static final class Shared<E> extends BackportReentrantLockFastTable<E> {
		private static final long serialVersionUID = 29253114956268253L;
		private final BackportReentrantLockFastTable<E> table;
		private final Lock rwLock;
		private Shared(BackportReentrantLockFastTable<E> target) {
			table = target;
			rwLock = new ReentrantLock();
		}
		@Override
		public boolean add(E value) {
			final Lock lock = rwLock;
			lock.lock();
			try {
				return table.add(value);
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
				table.clear();
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
				return table.contains(o);
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
				return table.get(index);
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
				return table.indexOf(o);
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
				return table.isEmpty();
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
				return table.remove(index);
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
				return table.remove(o);
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
				return table.size();
			}
			finally {
				lock.unlock();
			}
		}
	}
}