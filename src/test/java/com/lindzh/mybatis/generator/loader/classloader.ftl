package org.apache.catalina.loader.${package_suffix};

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.security.CodeSource;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.loader.AbstractWebappClassLoader;
import org.apache.catalina.loader.ResourceEntry;
import org.apache.catalina.loader.WebappClassLoader;
import org.apache.tomcat.util.codec.DecoderException;
import org.apache.tomcat.util.codec.binary.Base64;


public class CatalinaClassLoader_${class_suffix} extends AbstractWebappClassLoader{
	
    private static final int EOF = -1;
    
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
    
    private AbstractWebappClassLoader next = null;
    
    private WebappClassLoader rootLoader;
    
    private static final org.apache.juli.logging.Log log = org.apache.juli.logging.LogFactory.getLog(CatalinaClassLoader_${class_suffix}.class );
	
	public CatalinaClassLoader_${class_suffix}(){
		super();
	}
	
	public CatalinaClassLoader_${class_suffix}(ClassLoader parent){
		super(parent);
	}
	
	private String[] getSuffix_${method_suffix}(){
	
		return new String[]{"${support_file}"};
	}

	@Override
	public Class<?> findClass(String name) throws ClassNotFoundException {
		Class<?> result = null;
		if(name.startsWith("com.linda.koala.biz")){
			String fileName = this.genFilePath(name);
			try{
				File file = new File(fileName);
				if(!file.exists()){
					throw new ClassNotFoundException(name);
				}
				URL resource = file.toURL();
				ResourceEntry resourceEntry = new ResourceEntry();
				resourceEntry.source = resource;
				result = this.findClassInWebApp(name, resourceEntry);
			}catch(Exception e){
				
			}
		}else{
			result = this.rootLoader.rootLoadClass(name, false);
			if(result!=null){
				return result;
			}
		}
		if (result == null) {
			throw new ClassNotFoundException(name);
		}
		//log.info("[loader] ffff load class:" + name);
		return result;
	}
	
	private String toString(String[] arr){
		StringBuilder sb = new StringBuilder();
		for(String str:arr){
			sb.append(str);
			sb.append(",");
		}
		return sb.toString();
	}
	
	private int toDigit(final char ch, final int index) throws DecoderException {
		final int digit = Character.digit(ch, 16);
		if (digit == -1) {
			throw new DecoderException("Illegal hexadecimal character " + ch
					+ " at index " + index);
		}
		return digit;
	}
	
	private Class<?> findClassInWebApp(String name,ResourceEntry entry) throws ClassNotFoundException{
		String[] suffix = this.getSuffix_${method_suffix}();
		boolean find = false;
		if(entry!=null){
			find = true;
		}else{
			for(String signSuffix:suffix){
				entry = this.findClassResource(name, signSuffix);
				if(entry!=null&&entry.source!=null){
					find = true;
					break;
				}
			}
		}
		if(!find){
			if(next!=null){
				return next.findClass(name);
			}
			//log.info("[LOADER] find class "+name+" "+this.toString(suffix)+" null");
			throw new ClassNotFoundException(name);
		}else{
			//log.info("[LOADER] find class:"+name+" with "+this.toString(suffix)+" "+entry.source.toString());
			try {
				if(entry.binaryContent==null){
					InputStream ins = entry.source.openStream();
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					byte[] buf = new byte[DEFAULT_BUFFER_SIZE];
			        int n = 0;
			        while (EOF != (n = ins.read(buf))) {
			            bos.write(buf, 0, n);
			        }
					ins.close();
					entry.binaryContent = bos.toByteArray();
					bos.close();
				}
				
				String rr = "${rand2_str}";
				int from = ${rand1_length};
				String key_${method_suffix} = "${rand1_str}${key}${rand2_str}";
				int end = key_${method_suffix}.length()-rr.length();
				key_${method_suffix} = key_${method_suffix}.substring(from, end);
				
				try {
					char[] data = key_${method_suffix}.toCharArray();
					final int len = data.length;
					if ((len & 0x01) != 0) {
						throw new DecoderException("Odd number of characters.");
					}
					final byte[] bin = new byte[len >> 1];
					for (int i = 0, j = 0; j < len; i++) {
						int mm = ${rand1_length};
						int f = toDigit(data[j], j) << 4;
						j++;
						f = f | toDigit(data[j], j);
						j++;
						bin[i] = (byte) (f & 0xFF);
						i++;
						mm++;
						j = j+mm;
						mm--;
						j = j-mm-1;
						i--;
					}
					DESedeKeySpec keyspec = new DESedeKeySpec(bin);
					SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
					SecretKey key = keyfactory.generateSecret(keyspec);
					Base64 base64 = new Base64();
					byte[] content = base64.decode(entry.binaryContent);
					Cipher cipher = Cipher.getInstance("DESede");
					cipher.init(Cipher.DECRYPT_MODE, key);
					entry.binaryContent = cipher.doFinal(content);
				}catch(Exception e){
					throw new ClassNotFoundException(e.getMessage(),e);
				}
				//=====================解码结束======================
				
			    Class<?> clazz = entry.loadedClass;
		        if (clazz != null)
		            return clazz;

		        synchronized (this) {
		            clazz = entry.loadedClass;
		            if (clazz != null)
		                return clazz;

		            if (entry.binaryContent == null)
		                throw new ClassNotFoundException(name);

		            // Looking up the package
		            String packageName = null;
		            int pos = name.lastIndexOf('.');
		            if (pos != -1)
		                packageName = name.substring(0, pos);

		            Package pkg = null;

		            if (packageName != null) {
		                pkg = super.getPackage(packageName);
		                // Define the package (if null)
		                if (pkg == null) {
		                    try {
		                        if (entry.manifest == null) {
		                            super.definePackage(packageName, null, null, null, null,
		                                    null, null, null);
		                        } else {
		                        	super.definePackage(packageName, entry.manifest,entry.codeBase);
		                        }
		                    } catch (IllegalArgumentException e) {
		                        // Ignore: normal error due to dual definition of package
		                    }
		                    pkg = super.getPackage(packageName);
		                }
		            }

		            if (securityManager != null) {

		                // Checking sealing
		                if (pkg != null) {
		                    boolean sealCheck = true;
		                    if (pkg.isSealed()) {
		                        sealCheck = pkg.isSealed(entry.codeBase);
		                    } else {
		                        sealCheck = (entry.manifest == null)
		                            || !isPackageSealed(packageName, entry.manifest);
		                    }
		                    if (!sealCheck)
		                        throw new SecurityException
		                            ("Sealing violation loading " + name + " : Package "
		                             + packageName + " is sealed.");
		                }

		            }

		            try {
		            	if(entry.certificates==null&&entry.codeBase==null){
		            		clazz = this.defineClass(name, entry.binaryContent, 0, entry.binaryContent.length);
		            	}else{
			                clazz = defineClass(name, entry.binaryContent, 0,
			                        entry.binaryContent.length,
			                        new CodeSource(entry.codeBase, entry.certificates));
		            	}
		            } catch (UnsupportedClassVersionError ucve) {
		                throw new UnsupportedClassVersionError(
		                        ucve.getLocalizedMessage() + " " +
		                        sm.getString("webappClassLoader.wrongVersion",
		                                name));
		            }
		            entry.loadedClass = clazz;
		            entry.binaryContent = null;
		            entry.source = null;
		            entry.codeBase = null;
		            entry.manifest = null;
		            entry.certificates = null;
		        }
		        return clazz;
			} catch (IOException e) {
				throw new ClassNotFoundException("read "+entry.source.toString(),e);
			}
		}
	}
	
	protected ResourceEntry findClassResource(String name,String suffix)throws ClassNotFoundException {
        if (!validate(name))
            throw new ClassNotFoundException(name);

        String tempPath = name.replace('.', '/');
        String classPath = tempPath + suffix;
        ResourceEntry entry = null;
        //log.info("[LOADER] find:"+classPath);
        entry = super.findResourceInternal(name, classPath,false);
        if (entry == null){
        	 throw new ClassNotFoundException(name);	
        }
        return entry; 
	}
	
	
	public CatalinaClassLoader_${class_suffix} copyWithoutTransformers() {
		try{
			CatalinaClassLoader_${class_suffix} result = new CatalinaClassLoader_${class_suffix}(this.getParent());
			result.setRootLoader(rootLoader);
			super.copyStateWithoutTransformers(result);
			result.start();
			return result;
		}catch(Exception e){
			throw new IllegalStateException(e);
		}
	}
	
	@Override
	public AbstractWebappClassLoader next() {
		return next;
	}
	
	private static String genFilePath(String clazz){
		String replace = clazz.replace('.', File.separatorChar);
		if(clazz.startsWith("com.linda.koala.biz")){
			String ext = ".classx";
			return WebappClassLoader.baseDir+File.separator+replace+ext;
		}else{
			int index = clazz.lastIndexOf('_');
			String ext = ".class"+clazz.substring(index);
			return WebappClassLoader.baseDir+File.separator+replace+ext;
		}
	}
	
	@Override
	public void start() throws LifecycleException {
		super.start();
		String classLoaderName = "${subclass}";
		String fileName = this.genFilePath(classLoaderName);
		try{
			File file = new File(fileName);
			if(!file.exists()){
				return;
			}
			URL resource = file.toURL();
			ResourceEntry resourceEntry = new ResourceEntry();
			resourceEntry.source = resource;
			Class<?> subLoader = this.findClassInWebApp(classLoaderName, resourceEntry);
			if(subLoader!=null){
		        Class<?>[] argTypes = { ClassLoader.class };
		        Object[] args = { this.getParent() };
				Constructor<?> constructor = subLoader.getConstructor(argTypes);
				next = (AbstractWebappClassLoader)constructor.newInstance(args);
				next.setRootLoader(rootLoader);
				super.copyStateWithoutTransformers(next);
				next.start();
			}
		}catch(Exception e){
			
		}
	}
	
	@Override
	public Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {
		return this.rootLoadClass(name, true);
	}

	@Override
	public Class rootLoadClass(String name, boolean throwNotFound)
			throws ClassNotFoundException {
		return rootLoader.rootLoadClass(name, throwNotFound);
	}

	@Override
	public void setRootLoader(WebappClassLoader loader) {
		this.rootLoader = loader;
	}
}
