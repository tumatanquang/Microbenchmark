package uc.util;
import javolution.util.FastTable;
import javolution.util.ReentrantLock;
public class ReentrantLockFastTable<E> extends FastTable<E> {
	private static final long serialVersionUID = 2781771606854788734L;
	public ReentrantLockFastTable() {
		super();
	}
	public ReentrantLockFastTable(int capacity) {
		super(capacity);
	}
	@Override
	public final ReentrantLockFastTable<E> shared() {
		return new Shared<E>(this);
	}
	private static final class Shared<E> extends ReentrantLockFastTable<E> {
		private static final long serialVersionUID = 29253114956268253L;
		private final ReentrantLockFastTable<E> table;
		private final ReentrantLock rwLock;
		private Shared(ReentrantLockFastTable<E> target) {
			table = target;
			rwLock = new ReentrantLock();
		}
		@Override
		public boolean add(E value) {
			final ReentrantLock lock = rwLock;
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
			final ReentrantLock lock = rwLock;
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
			final ReentrantLock lock = rwLock;
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
			final ReentrantLock lock = rwLock;
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
			final ReentrantLock lock = rwLock;
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
			final ReentrantLock lock = rwLock;
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
			final ReentrantLock lock = rwLock;
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
			final ReentrantLock lock = rwLock;
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
			final ReentrantLock lock = rwLock;
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