package molab.main.java.dao;

import java.util.List;

import molab.main.java.entity.ICtDispatcher;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * A data access object (DAO) providing persistence and search support for
 * CtDispatcher entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see molab.main.java.entity.ICtDispatcher
 * @author MyEclipse Persistence Tools
 */
@Repository
public class ICtDispatcherDao extends BaseDao<ICtDispatcher> {
	private static final Logger log = LoggerFactory
			.getLogger(ICtDispatcherDao.class);
	// property constants
	public static final String USER_ID = "userId";
	public static final String APPLICATION_ID = "applicationId";
	public static final String OPR_TIME = "oprTime";
	public static final String STATE = "state";

	public ICtDispatcher findById(java.lang.Integer id) {
		log.debug("getting ICtDispatcher instance with id: " + id);
		try {
			ICtDispatcher instance = (ICtDispatcher) getHibernateTemplate().get(
					"molab.main.java.entity.ICtDispatcher", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<ICtDispatcher> findByExample(ICtDispatcher instance) {
		log.debug("finding ICtDispatcher instance by example");
		try {
			List<ICtDispatcher> results = (List<ICtDispatcher>) getHibernateTemplate()
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
		log.debug("finding ICtDispatcher instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ICtDispatcher as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<ICtDispatcher> findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}

	public List<ICtDispatcher> findByApplicationId(Object applicationId) {
		return findByProperty(APPLICATION_ID, applicationId);
	}

	public List<ICtDispatcher> findByOprTime(Object oprTime) {
		return findByProperty(OPR_TIME, oprTime);
	}

	public List<ICtDispatcher> findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List findAll() {
		log.debug("finding all ICtDispatcher instances");
		try {
			String queryString = "from ICtDispatcher";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ICtDispatcher merge(ICtDispatcher detachedInstance) {
		log.debug("merging ICtDispatcher instance");
		try {
			ICtDispatcher result = (ICtDispatcher) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ICtDispatcher instance) {
		log.debug("attaching dirty ICtDispatcher instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ICtDispatcher instance) {
		log.debug("attaching clean ICtDispatcher instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}