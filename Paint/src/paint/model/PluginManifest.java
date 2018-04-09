package paint.model;

public class PluginManifest {
	
	private String name,className,packageName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public PluginManifest(String name, String className, String packageName) {
		this.name = name;
		this.className = className;
		this.packageName = packageName;
	}
	
}
