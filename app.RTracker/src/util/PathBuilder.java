package util;

import java.io.File;
import java.util.ArrayList;



/**
 * StringBuilderの、パスに特化したバージョン
 * @author arata.hasebe
 */
public class PathBuilder {
	private StringBuilder buf = new StringBuilder();
	public static final char DELIMITER		= '/';
	public static final String STR_DELIMITER	= "/";
	ArrayList list = new ArrayList();
	
	/**
	 * Constructor
	 *
	 */
	public PathBuilder() {
	}
	
	public PathBuilder(String path) {
		append(path);
	}
	
	/**
	 * equal to new
	 *
	 */
	public void reset() {
		buf = new StringBuilder();
		list = new ArrayList();
	}
	
	/**
	 * appender
	 * 
	 * path.append("/var").append("rio").append("conf").toString() → "/var/rio/conf"
	 * @param path
	 * @return
	 */
	public PathBuilder append(String path) {
		if(path == null || path.length() == 0) return this;
		
		if(buf.length() > 0 && buf.charAt(buf.length() - 1) != DELIMITER && path.charAt(0) != DELIMITER) {
			buf.append(DELIMITER);
		}
		
		buf.append(path);
		
		list.add(path);
		
		return this;
	}
	public PathBuilder append(int path) {
		return append(Integer.toString(path));
	}
	
	/**
	 * ディレクトリがなければ作成（サブディレクトリを含めて一括作成）
	 *
	 */
	public boolean mkdir() {
		// 既にあればよい
		if(isDir() || isFile()) return true;
		
		PathBuilder path = new PathBuilder();
		for(int i = 0; i < list.size(); i++) {
			String dir = (String)list.get(i);
			path.append(dir);
			if(i > 0 && path.isDir() == false) {
				File file = new File(path.toString());
				if(file.mkdir() == false) return false;
			}
		}
		
		return true;
	}
	
	/**
	 * ファイルが存在するかどうか？
	 * @param path
	 * @return
	 */
	public boolean isFile(){
		if(buf.length() == 0) return false;
		File file = new File(buf.toString());
		return (file.exists() && file.isFile());
	}
	
	/**
	 * ディレクトリが存在するかどうか？
	 * @param path
	 * @return
	 */
	public boolean isDir(){
		if(buf.length() == 0) return false;
		File file = new File(buf.toString());
		return (file.exists() && file.isDirectory());
	}
	
	/**
	 * return result
	 */
	public String toString() {
		return buf.toString();
	}
	
	public String toString(String append) {
		StringBuilder b = new StringBuilder(buf);
		
		if(b.length() > 0 && b.charAt(b.length() - 1) != DELIMITER && append.charAt(0) != DELIMITER) {
			b.append(DELIMITER);
		}
		
		b.append(append);
		return b.toString();
	}
}
