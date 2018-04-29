package paint.model;

import java.io.File;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import paint.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import paint.controller.ShapesController;
import paint.view.Main;

public class PluginManager {
	public ArrayList<CommandPane> loadPlugins() {

		ArrayList<CommandPane> commands = new ArrayList<>();
		String Path = System.getProperty("user.dir") + "/";
		URL dirUrl = null;
		try {
			dirUrl = new URL("file:///" + Path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print("\nOpening " + Path + "src" + File.separator + "resources" + File.separator + "plugins" + "  for plugins\n");

		try {
			/*File file = new File(getClass().getResource("/plugins").getPath());
			System.out.println(getClass().getResource("/plugins").getPath());
			System.out.println("class resource :: " + getClass().getResource(""));
			String path = getClass().getResource("/plugins").getPath();*/
			File file = new File(dirUrl.getPath()+"plugins");
			System.out.println(dirUrl.getPath()+"plugins");
			System.out.println("class resource :: " + dirUrl.getPath()+"plugins");
			String path = dirUrl.getPath()+"plugins";
			String[] shapesNames = file.list();
			for (String name : shapesNames) {
				
				/*if (new File(dirUrl.getPath()).isDirectory() && name.charAt(0) != '.') {
					CommandPane newTool = loadTool(name);
					if (newTool != null) {

						commands.add(newTool);
					}
				}*/
				CommandPane newTool = getClassFromJar(path+"/"+name);
				commands.add(newTool);
			}
		} catch (NullPointerException e) {
			System.out.print("Plugins directory not found!\nCannot initialize plugins");
		}

		return commands;
	}

	private PluginManifest getManifest(String tool) {

		URL jsonUrl = null;
		try {
			jsonUrl = new URL("file:///" + System.getProperty("user.dir") + File.separator + "src" + File.separator
					+ "paint" +  File.separator + "plugin" + File.separator + tool + File.separator + "manifest.json");
		} catch (MalformedURLException e) {
			System.out.print("Cannot load " + tool + "'s manifest.json!");
		}
		try (Reader reader = new InputStreamReader(jsonUrl.openStream(), "UTF-8")) {
			Gson gson = new GsonBuilder().create();
			PluginManifest manifest = gson.fromJson(reader, PluginManifest.class);
			return manifest;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	private CommandPane loadTool(String tool) {

		CommandPane newCommand = null;
		PluginManifest manifest = getManifest(tool);
		if (manifest == null) {
			System.out.print("Unable to load " + tool);
			return null;
		}
		String className = null;
		String packageName = null;
		String toolName = null;
		URL classUrl = null;

		className = manifest.getClassName();
		packageName = manifest.getPackageName();
		toolName = manifest.getName();

		if (className != null && packageName != null) {
			try {
				classUrl = new URL("file:///" + System.getProperty("user.dir") + File.separator + "paint" +
			File.separator + "plugin" + File.separator + tool + File.separator + className + ".class");
				System.out.print(classUrl);

			} catch (MalformedURLException e) {
				System.out.print("Unable to load " + tool);
			}
			URL[] urls = new URL[] { classUrl };
			@SuppressWarnings("resource")
			ClassLoader classLoader = new URLClassLoader(urls);
			try {
				Class<?> cls = classLoader.loadClass(packageName + "." + className);
				newCommand = (CommandPane) cls.newInstance();
				ShapesController.getInstance(Main.getController()).addLoader(new CustomClassLoader(manifest, classLoader,newCommand.getToolClass()));
				System.out.print("Initialized " + toolName + "!\n");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return newCommand;
	}
	public CommandPane getClassFromJar(String jarPath) {
		CommandPane newCommand = null;
		try {
			URL[] urls = { new URL("jar:file:" + jarPath+"!/") };
			URLClassLoader classLoader = URLClassLoader.newInstance(urls);
			//URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] {myJarFile});
			
			PluginManifest  manifest = getManifestFromJar(jarPath);
			System.out.println(manifest.getClassName());
			System.out.println("*** URL : : " + urls[0].getPath());
			Class<?> mClass = classLoader.loadClass(manifest.getPackageName() + "."+manifest.getClassName());
			newCommand = (CommandPane) mClass.newInstance();
			ShapesController.getInstance(Main.getController()).addLoader(new CustomClassLoader(manifest, classLoader,newCommand.getToolClass()));
			return newCommand;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newCommand;
	}
	@SuppressWarnings("resource")
	public PluginManifest getManifestFromJar(String jarPath) {
		try {
			ZipFile  file = new ZipFile(jarPath);
			if (file != null) {
				System.out.println("Entered file :: " + jarPath);
			   Enumeration<? extends ZipEntry> entries = file.entries();
			   if (entries != null) {
			      while (entries.hasMoreElements()) {
			          ZipEntry entry = entries.nextElement();
			         if(entry.getName().contains("manifest.json")) {
			        	 try (Reader reader = new InputStreamReader(file.getInputStream(entry), "UTF-8")) {
			     			Gson gson = new GsonBuilder().create();
			     			PluginManifest manifest = gson.fromJson(reader, PluginManifest.class);
			     			System.out.println(manifest.getName());
			     			return manifest;
			     		} catch (Exception e) {
			     			// TODO: handle exception
			     			e.printStackTrace();
			     		}
			         }
			      	}
			    }
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
}
