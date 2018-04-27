package paint.model;


public class CustomClassLoader {
private PluginManifest manifest;
private Class<?> mClass;
private ClassLoader classLoader; 
public CustomClassLoader(PluginManifest manifest , ClassLoader classLoader,Class<?> mClass) {
	this.manifest = manifest;
	this.classLoader = classLoader;
	this.mClass = mClass;
	System.out.println(mClass);
}
public Class<?> getClassObject() throws ClassNotFoundException{
	return classLoader.loadClass(manifest.getPackageName()+"."+manifest.getClassName());
}
public String getClassName() {
	return manifest.getClassName();
}
public PluginManifest getManifest() {
	return this.manifest;
}
public Class<?> getmClass(){
	return this.mClass;
}
}
