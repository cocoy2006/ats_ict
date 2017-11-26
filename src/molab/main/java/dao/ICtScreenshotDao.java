package molab.main.java.dao;

import java.util.List;

import molab.main.java.entity.ICtScreenshot;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * A data access object (DAO) providing persistence and search support for
 * ICtScreenshot entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see molab.main.java.entity.ICtScreenshot
 * @author MyEclipse Persistence Tools
 */
@Repository
public class ICtScreenshotDao extends BaseDao<ICtScreenshot> {
	private static final Logger log = LoggerFactory
			.getLogger(ICtScreenshotDao.class);
	// property constants
	public static final String RUNNER_ID = "runnerId";
	public static final String IMAGE = "image";
	public static final String COMMENT = "comment";
	public static final String OPR_TIME = "oprTime";

	public ICtScreenshot findById(java.lang.Integer id) {
		log.debug("getting ICtScreenshot instance with id: " + id);
		try {
			ICtScreenshot instance = (ICtScreenshot) getHibernateTemplate().get(
					"molab.main.java.entity.ICtScreenshot", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<ICtScreenshot> findByExample(ICtScreenshot instance) {
		log.debug("finding ICtScreenshot instance by example");
		try {
			List<ICtScreenshot> results = (List<ICtScreenshot>) getHibernateTemplate()
					.findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding ICtScreenshot instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ICtScreenshot as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<ICtScreenshot> findByRunnerId(Object runnerId) {
		return findByProperty(RUNNER_ID, runnerId);
	}

	public List<ICtScreenshot> findByImage(Object image) {
		return findByProperty(IMAGE, image);
	}

	public List<ICtScreenshot> findByComment(Object comment) {
		return findByProperty(COMMENT, comment);
	}

	public List<ICtScreenshot> findByOprTime(Object oprTime) {
		return findByProperty(OPR_TIME, oprTime);
	}

	public List findAll() {
		log.debug("finding all ICtScreenshot instances");
		try {
			String queryString = "from ICtScreenshot";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ICtScreenshot merge(ICtScreenshot detachedInstance) {
		log.debug("merging ICtScreenshot instance");
		try {
			ICtScreenshot result = (ICtScreenshot) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ICtScreenshot instance) {
		log.debug("attaching dirty ICtScreenshot instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ICtScreenshot instance) {
		log.debug("attaching clean ICtScreenshot instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}