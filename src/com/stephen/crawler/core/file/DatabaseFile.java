package com.stephen.crawler.core.file;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.stephen.crawler.core.Result;
import com.stephen.crawler.core.base.BaseFile;

public class DatabaseFile extends BaseFile {

	private String resource;
	private InputStream inputStream;
	private SqlSessionFactory sessionFactory;
	private SqlSession session;
	private Class<?> cls = null;
	private Class<?> mapperCls = null;
	private Method method = null;
	private Object mapper;

	public DatabaseFile(Result c) throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException {
		super(c);
		resource = c.getConfig().getMybatisConfPath();
		inputStream = DatabaseFile.class.getClassLoader().getResourceAsStream(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		session = sessionFactory.openSession();

		cls = c.getConfig().getOrmCls();
		setFileName(cls.getName());
		System.out.println("创建新连接，新表对应的类为" + cls.getName());
		mapperCls = Class.forName(cls.getName() + "Mapper");
		method = mapperCls.getDeclaredMethod("insert", cls);
		mapper = session.getMapper(mapperCls);
	}

	@Override
	public boolean save(Result re) {
		List<?> list = re.getResultList();
		try {
			// SqlSession session = sessionFactory.openSession();
			if (list != null && !list.isEmpty()) {
				// Object mObject = null;
				for (Object object : list) {
					mapper = session.getMapper(mapperCls);
					method.invoke(mapper, object);
					session.commit();
				}
			}
			// session.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		session.close();
		inputStream.close();
	}

}
