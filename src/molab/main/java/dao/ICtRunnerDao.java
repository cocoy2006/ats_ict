package molab.main.java.dao;

import java.util.List;

import molab.main.java.entity.ICtRunner;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * A data access object (DAO) providing persistence and search support for
 * ICtRunner entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see molab.main.java.entity.ICtRunner
 * @author MyEclipse Persistence Tools
 */
@Repository
public class ICtRunnerDao extends BaseDao<ICtRunner> {
	private static final Logger log = LoggerFactory
			.getLogger(ICtRunnerDao.class);
	// property constants
	public static final String DISPATCHER_ID = "dispatcherId";
	public static final String DEVICE_ID = "deviceId";
	public static final String AVERAGE_CPU = "averageCpu";
	public static final String AVERAGE_MEMORY = "averageMemory";
	public static final String INSTALL_TIME = "installTime";
	public static final String INSTALL_RESULT = "installResult";
	public static final String LOAD_TIME = "loadTime";
	public static final String UNINSTALL_TIME = "uninstallTime";
	public static final String LOGCAT = "logcat";
	public static final String STATE = "state";
	
	public ICtRunner findById(java.lang.Integer id) {
		log.debug("getting ICtRunner instance with id: " + id);
		try {
			ICtRunner instance = (ICtRunner) getHibernateTemplate().get(
					"molab.main.java.entity.ICtRunner", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<ICtRunner> findByExample(ICtRunner instance) {
		log.debug("finding ICtRunner instance by example");
		try {
			List<ICtRunner> results = (List<ICtRunner>) getHibernateTemplate()
					.findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<ICtRunner> findByProperty(String propertyName, Object value) {
		log.debug("finding ICtRunner instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from ICtRunner as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<ICtRunner> findByDispatcherId(Object dispatcherId) {
		return findByProperty(DISPATCHER_ID, dispatcherId);
	}

	public List<ICtRunner> findByDeviceId(Object deviceId) {
		return findByProperty(DEVICE_ID, deviceId);
	}

	public List<ICtRunner> findByAverageCpu(Object averageCpu) {
		return findByProperty(AVERAGE_CPU, averageCpu);
	}

	public List<ICtRunner> findByAverageMemory(Object averageMemory) {
		return findByProperty(AVERAGE_MEMORY, averageMemory);
	}

	public List<ICtRunner> findByInstallTime(Object installTime) {
		return findByProperty(INSTALL_TIME, installTime);
	}

	public List<ICtRunner> findByInstallResult(Object installResult) {
		return findByProperty(INSTALL_RESULT, installResult);
	}

	public List<ICtRunner> findByLoadTime(Object loadTime) {
		return findByProperty(LOAD_TIME, loadTime);
	}

	public List<ICtRunner> findByUninstallTime(Object uninstallTime) {
		return findByProperty(UNINSTALL_TIME, uninstallTime);
	}

	public List<ICtRunner> findByLogcat(Object logcat) {
		return findByProperty(LOGCAT, logcat);
	}

	public List<ICtRunner> findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List<ICtRunner> findAll() {
		log.debug("finding all ICtRunner instances");
		try {
			String queryString = "from ICtRunner";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ICtRunner merge(ICtRunner detachedInstance) {
		log.debug("merging ICtRunner instance");
		try {
			ICtRunner result = (ICtRunner) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ICtRunner instance) {
		log.debug("attaching dirty ICtRunner instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ICtRunner instance) {
		log.debug("attaching clean ICtRunner instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<ICtRunner> findByState(int state, String server, int port) {
		String hql = "from ICtRunner where state=? and deviceId in (select id from ICtDevice where server=? and port=?)";
		return getHibernateTemplate().find(hql, state, server, port);
	}
	
}