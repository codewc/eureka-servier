package com.example.demo.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

@Repository("basicDao")
public class GenericHibernateDao <T extends Serializable, PK extends Serializable> extends HibernateDaoSupport implements GenericDao<T, PK> {
	 // 实体类类型(由构造方法自己主动赋值)
    private Class<T> entityClass;

    @Resource
    protected SessionFactory sessionFactory;
    
    // 构造方法，依据实例类自己主动获取实体类类型
    public GenericHibernateDao() {
        this.entityClass = null;
        Class c = getClass();
        Type t = c.getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) t).getActualTypeArguments();
            this.entityClass = (Class<T>) p[0];
        }
    }

    // -------------------- 基本检索、添加、改动、删除操作 --------------------

    // 依据主键获取实体。假设没有对应的实体。返回 null。


    public T get(PK id) {
        return (T) getHibernateTemplate().get(entityClass, id);
    }

    // 依据主键获取实体并加锁。假设没有对应的实体，返回 null。
    public T getWithLock(PK id, LockMode lock) {
        T t = (T) getHibernateTemplate().get(entityClass, id, lock);
        if (t != null) {
            this.flush(); // 马上刷新，否则锁不会生效。
        }
        return t;
    }

    // 依据主键获取实体。假设没有对应的实体，抛出异常。


    public T load(PK id) {
        return (T) getHibernateTemplate().load(entityClass, id);
    }

    // 依据主键获取实体并加锁。假设没有对应的实体，抛出异常。
    public T loadWithLock(PK id, LockMode lock) {
        T t = (T) getHibernateTemplate().load(entityClass, id, lock);
        if (t != null) {
            this.flush(); // 马上刷新，否则锁不会生效。
        }
        return t;
    }

    // 获取所有实体。


    public List<T> loadAll() {
        return (List<T>) getHibernateTemplate().loadAll(entityClass);
    }

    // loadAllWithLock() ?

    // 更新实体
    public void update(T entity) {
        getHibernateTemplate().update(entity);
    }

    // 更新实体并加锁
    public void updateWithLock(T entity, LockMode lock) {
        getHibernateTemplate().update(entity, lock);
        this.flush(); // 马上刷新，否则锁不会生效。
    }

    // 存储实体到数据库
    public void save(T entity) {
        getHibernateTemplate().save(entity);
    }

    // saveWithLock()？

    // 添加或更新实体
    public void saveOrUpdate(T entity) {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    // 添加或更新集合中的所有实体
    public void saveOrUpdateAll(Collection<T> entities) {
    	for (T entity :entities) {
    		getHibernateTemplate().saveOrUpdate(entity);
    	}
    }

    // 删除指定的实体
    public void delete(T entity) {
        getHibernateTemplate().delete(entity);
    }

    // 加锁并删除指定的实体
    public void deleteWithLock(T entity, LockMode lock) {
        getHibernateTemplate().delete(entity, lock);
        this.flush(); // 马上刷新，否则锁不会生效。


 

    }

    // 依据主键删除指定实体
    public void deleteByKey(PK id) {
        this.delete(this.load(id));
    }

    // 依据主键加锁并删除指定的实体
    public void deleteByKeyWithLock(PK id, LockMode lock) {
        this.deleteWithLock(this.load(id), lock);
    }

    // 删除集合中的所有实体
    public void deleteAll(Collection<T> entities) {
        getHibernateTemplate().deleteAll(entities);
    }

    // -------------------- HQL ----------------------------------------------

    // 使用HSQL语句直接添加、更新、删除实体
    public int bulkUpdate(String queryString) {
        return getHibernateTemplate().bulkUpdate(queryString);
    }

    // 使用带參数的HQL语句添加、更新、删除实体
    public int bulkUpdate(String queryString, Object[] values) {
        return getHibernateTemplate().bulkUpdate(queryString, values);
    }

    // 使用HQL语句检索数据
    public List find(String queryString) {
        return getHibernateTemplate().find(queryString);
    }

    // 使用带參数的HQL语句检索数据
    public List find(String queryString, Object[] values) {
        return getHibernateTemplate().find(queryString, values);
    }

    // 使用带命名的參数的HQL语句检索数据
    public List findByNamedParam(String queryString, String[] paramNames,  Object[] values) {
        return getHibernateTemplate().findByNamedParam(queryString, paramNames,values);
    }

    // 使用命名的HSQL语句检索数据
    public List findByNamedQuery(String queryName) {
        return getHibernateTemplate().findByNamedQuery(queryName);
    }

    // 使用带參数的命名HQL语句检索数据
    public List findByNamedQuery(String queryName, Object[] values) {
        return getHibernateTemplate().findByNamedQuery(queryName, values);
    }

    // 使用带命名參数的命名HQL语句检索数据
    public List findByNamedQueryAndNamedParam(String queryName, String[] paramNames, Object[] values) {
        return getHibernateTemplate().findByNamedQueryAndNamedParam(queryName,paramNames, values);
    }

    // 使用HQL语句检索数据，返回 Iterator
    public Iterator iterate(String queryString) {
        return getHibernateTemplate().iterate(queryString);
    }

    // 使用带參数HQL语句检索数据。返回 Iterator
    public Iterator iterate(String queryString, Object[] values) {
        return getHibernateTemplate().iterate(queryString, values);
    }

    // 关闭检索返回的 Iterator
    public void closeIterator(Iterator it) {
        getHibernateTemplate().closeIterator(it);
    }

    // -------------------------------- Criteria ------------------------------

    // 创建与会话无关的检索标准
    public DetachedCriteria createDetachedCriteria() {
        return DetachedCriteria.forClass(this.entityClass);
    }

    // 创建与会话绑定的检索标准
    public Criteria createCriteria() {
        return this.createDetachedCriteria().getExecutableCriteria(
                //this.getSession()
                this.currentSession()
                );
    }

    // 检索满足标准的数据
    public List findByCriteria(DetachedCriteria criteria) {
        return getHibernateTemplate().findByCriteria(criteria);
    }

    // 检索满足标准的数据，返回指定范围的记录
    public List findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults) {
        return getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
    }

    // 使用指定的实体及属性检索（满足除主键外属性＝实体值）数据
    public List<T> findEqualByEntity(T entity, String[] propertyNames) {
        Criteria criteria = this.createCriteria();
        Example exam = Example.create(entity);
        exam.excludeZeroes();
        String[] defPropertys = getSessionFactory().getClassMetadata(
                entityClass).getPropertyNames();
        for (String defProperty : defPropertys) {
            int ii = 0;
            for (ii = 0; ii < propertyNames.length; ++ii) {
                if (defProperty.equals(propertyNames[ii])) {
                    criteria.addOrder(Order.asc(defProperty));
                    break;
                }
            }
            if (ii == propertyNames.length) {
                exam.excludeProperty(defProperty);
            }
        }
        criteria.add(exam);
        return (List<T>) criteria.list();
    }

    // 使用指定的实体及属性检索（满足属性 like 串实体值）数据
    @Deprecated
    public List<T> findLikeByEntity(T entity, String[] propertyNames) {
    	/*
        Criteria criteria = this.createCriteria();
        for (String property : propertyNames) {
            try {
                Object value = PropertyUtils.getProperty(entity, property);
                if (value instanceof String) {
                    criteria.add(Restrictions.like(property, (String) value,
                            MatchMode.ANYWHERE));
                    criteria.addOrder(Order.asc(property));
                } else {
                    criteria.add(Restrictions.eq(property, value));
                    criteria.addOrder(Order.asc(property));
                }
            } catch (Exception ex) {
                // 忽略无效的检索參考数据。


 

            }
        }
        return (List<T>) criteria.list();
        */
    	throw new RuntimeException("不支持的实现方法");
    }

    // 使用指定的检索标准获取满足标准的记录数
    public Integer getRowCount(DetachedCriteria criteria) {
        criteria.setProjection(Projections.rowCount());
        List list = this.findByCriteria(criteria, 0, 1);
        return (Integer) list.get(0);
    }

    // 使用指定的检索标准检索数据，返回指定统计值(max,min,avg,sum)
    public Object getStatValue(DetachedCriteria criteria, String propertyName,
            String StatName) {
        if (StatName.toLowerCase().equals("max"))
            criteria.setProjection(Projections.max(propertyName));
        else if (StatName.toLowerCase().equals("min"))
            criteria.setProjection(Projections.min(propertyName));
        else if (StatName.toLowerCase().equals("avg"))
            criteria.setProjection(Projections.avg(propertyName));
        else if (StatName.toLowerCase().equals("sum"))
            criteria.setProjection(Projections.sum(propertyName));
        else
            return null;
        List list = this.findByCriteria(criteria, 0, 1);
        return list.get(0);
    }

    // -------------------------------- Others --------------------------------

    // 加锁指定的实体
    public void lock(T entity, LockMode lock) {
        getHibernateTemplate().lock(entity, lock);
    }

    // 强制初始化指定的实体
    public void initialize(Object proxy) {
        getHibernateTemplate().initialize(proxy);
    }

    // 强制马上更新缓冲数据到数据库（否则仅在事务提交时才更新）
    public void flush() {
        getHibernateTemplate().flush();
    }
    
    /**
     * 解决sessionFactory找寻不到
     * @param sessionFactory
     */
    @Autowired  
    public void setSessionFactoryOverride(SessionFactory sessionFactory) {  
        super.setSessionFactory(sessionFactory);  
    } 

}
