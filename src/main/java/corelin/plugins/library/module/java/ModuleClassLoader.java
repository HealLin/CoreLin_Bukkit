package corelin.plugins.library.module.java;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

/**
 * @author 择忆霖心
 * @简述 TODO
 * @时间 2020/11/22 23:02
 * @版本 1.0
 */
public class ModuleClassLoader extends URLClassLoader {


    private final Map<String, Class<?>> classes = new java.util.concurrent.ConcurrentHashMap<String, Class<?>>(); // Spigot

    public ModuleClassLoader(File file, ClassLoader parent) throws MalformedURLException {
        super(new URL[] {file.toURI().toURL()}, parent);
      //  this.loader = parent;
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }



    /*Class<?> findClass(String name, boolean checkGlobal) throws ClassNotFoundException {
        if (name.startsWith("org.bukkit.") || name.startsWith("net.minecraft.")) {
            throw new ClassNotFoundException(name);
        }
        Class<?> result = classes.get(name);

        if (result == null) {
            if (checkGlobal) {
                result = loader.getClassByName(name);
            }

            if (result == null) {
                String path = name.replace('.', '/').concat(".class");
                JarEntry entry = jar.getJarEntry(path);

                if (entry != null) {
                    byte[] classBytes;

                    try (InputStream is = jar.getInputStream(entry)) {
                        classBytes = ByteStreams.toByteArray(is);
                    } catch (IOException ex) {
                        throw new ClassNotFoundException(name, ex);
                    }

                    int dot = name.lastIndexOf('.');
                    if (dot != -1) {
                        String pkgName = name.substring(0, dot);
                        if (getPackage(pkgName) == null) {
                            try {
                                if (manifest != null) {
                                    definePackage(pkgName, manifest, url);
                                } else {
                                    definePackage(pkgName, null, null, null, null, null, null, null);
                                }
                            } catch (IllegalArgumentException ex) {
                                if (getPackage(pkgName) == null) {
                                    throw new IllegalStateException("Cannot find package " + pkgName);
                                }
                            }
                        }
                    }

                    CodeSigner[] signers = entry.getCodeSigners();
                    CodeSource source = new CodeSource(url, signers);

                    result = defineClass(name, classBytes, 0, classBytes.length, source);
                }

                if (result == null) {
                    result = super.findClass(name);
                }

                if (result != null) {
                    loader.setClass(name, result);
                }
            }

            classes.put(name, result);
        }

        return result;
    }*/



}
