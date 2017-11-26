package molab.main.java.dao;

import java.util.List;

import molab.main.java.entity.Application;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * A data access object (DAO) providing persistence and search support for
 * Application entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see molab.main.java.entity.Application
 * @author MyEclipse Persistence Tools
 */
@Repository
public class ApplicationDao extends BaseDao<Application> {
	private static final Logger log = LoggerFactory
			.getLogger(ApplicationDao.class);
	// property constants
	public static final String MD5 = "md5";
	public static final String NAME = "name";
	public static final String ALIASNAME = "aliasname";
	public static final String SIZE = "size";
	public static final String PACKAGENAME = "packagename";
	public static final String VERSION = "version";
	public static final String OS = "os";
	public static final String STARTACTIVITY = "startactivity";
	public static final String ICON = "icon";

	public Application findById(java.lang.Integer id) {
		log.debug("getting Application instance with id: " + id);
		try {
			Application instance = (Application) getHibernateTemplate().get(
					"molab.main.java.entity.Application", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Application> findByExample(Application instance) {
		log.debug("finding Application instance by example");
		try {
			List<Application> results = (List<Application>) getHibernateTemplate()
					.findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<Application> findByProperty(String propertyName, Object value) {
		log.debug("finding Application instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Application as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Application> findByMd5(Object md5) {
		return findByProperty(MD5, md5);
	}

	public List<Application> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<Application> findByAliasname(Object aliasname) {
		return findByProperty(ALIASNAME, aliasname);
	}

	public List<Application> findBySize(Object size) {
		return findByProperty(SIZE, size);
	}

	public List<Application> findByPackagename(Object packagename) {
		return findByProperty(PACKAGENAME, packagename);
	}

	public List<Application> findByVersion(Object version) {
		return findByProperty(VERSION, version);
	}

	public List<Application> findByOs(Object os) {
		return findByProperty(OS, os);
	}

	public List<Application> findByStartactivity(Object startactivity) {
		return findByProperty(STARTACTIVITY, startactivity);
	}

	public List<Application> findByIcon(Object icon) {
		return findByProperty(ICON, icon);
	}

	public List<Application> findAll() {
		log.debug("finding all Application instances");
		try {
			String queryString = "from Application";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Application merge(Application detachedInstance) {
		log.debug("merging Application instance");
		try {
			Application result = (Application) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Application instance) {
		log.debug("attaching dirty Application instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Application instance) {
		log.debug("attaching clean Application instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public Application findByRunnerId(int runnerId) {
		String hql = "FROM Application WHERE id=(SELECT applicationId FROM ICtDispatcher WHERE id=(SELECT dispatcherId FROM ICtRunner WHERE id=?))";
		List<Application> list = getHibernateTemplate().find(hql, runnerId);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
}