package spet.sbwo.control.util;

import static spet.sbwo.control.util.FileNameUtils.base;
import static spet.sbwo.control.util.FileNameUtils.extension;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FolderScanner {
	private File base;

	public FolderScanner(String base) {
		this.base = new File(base);
	}

	public static RootResult roots() {
		File[] files = File.listRoots();
		if (files != null) {
			return new RootResult(Arrays.stream(files).map(f -> f.getPath()).collect(Collectors.toList()),
					File.separator);
		} else {
			return new RootResult(Collections.emptyList(), File.separator);
		}
	}

	public QueryResult<String> folders() {
		File[] files = base.listFiles(f -> f.isDirectory() && !f.isHidden());
		if (files != null) {
			return new QueryResult<>(base.getAbsolutePath(),
					Arrays.stream(files).map(f -> f.getName()).collect(Collectors.toList()));
		} else {
			return new QueryResult<>(base.getAbsolutePath(), Collections.emptyList());
		}
	}

	public QueryResult<FileInfo> files(String extension) {
		File[] files = base
				.listFiles((f, n) -> (extension.isEmpty() || extension(n).equals(extension)) && !f.isHidden());
		if (files != null) {
			return new QueryResult<>(base.getAbsolutePath(),
					Arrays.stream(files).map(f -> new FileInfo(f)).collect(Collectors.toList()));
		} else {
			return new QueryResult<>(base.getAbsolutePath(), Collections.emptyList());
		}
	}

	public static class RootResult {
		private List<String> roots;
		private String separator;

		public RootResult(List<String> roots, String separator) {
			this.roots = roots;
			this.separator = separator;
		}

		public List<String> getRoots() {
			return roots;
		}

		public String getSeparator() {
			return separator;
		}
	}

	public static class QueryResult<T> {
		private String base;
		private List<T> content;

		public QueryResult(String base, List<T> content) {
			this.base = base;
			this.content = content;
		}

		public String getBase() {
			return base;
		}

		public List<T> getContent() {
			return content;
		}
	}

	public static class FileInfo {
		private String name;
		private String extension;
		private long size;

		public FileInfo(File file) {
			super();
			this.name = base(file.getName());
			this.extension = extension(file.getName());
			this.size = file.length();
		}

		public FileInfo(String name, String extension, long size) {
			super();
			this.name = name;
			this.extension = extension;
			this.size = size;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getExtension() {
			return extension;
		}

		public void setExtension(String extension) {
			this.extension = extension;
		}

		public long getSize() {
			return size;
		}

		public void setSize(long size) {
			this.size = size;
		}
	}
}
