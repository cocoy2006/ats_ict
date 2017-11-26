package molab.main.java.dao;

import java.util.List;

import molab.main.java.entity.ICtDevice;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * A data access object (DAO) providing persistence and search support for
 * ICtDevice entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see molab.main.java.entity.ICtDevice
 * @author MyEclipse Persistence Tools
 */
@Repository
public class ICtDeviceDao extends BaseDao<ICtDevice> {
	private static final Logger log = LoggerFactory
			.getLogger(ICtDeviceDao.class);
	// property constants
	public static final String SERVER = "server";
	public static final String PORT = "port";
	public static final String SN = "sn";
	public static final String MANUFACTURE_ID = "manufactureId";
	public static final String MODEL_ID = "modelId";
	public static final String OS = "os";
	public static final String IMEI = "imei";
	public static final String USES = "uses";
	public static final String STATE = "state";

	public ICtDevice findById(java.lang.Integer id) {
		log.debug("getting ICtDevice instance with id: " + id);
		try {
			ICtDevice instance = (ICtDevice) getHibernateTemplate().get(
					"molab.main.java.entity.ICtDevice", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<ICtDevice> findByExample(ICtDevice instance) {
		log.debug("finding ICtDevice instance by example");
		try {
			List<ICtDevice> results = (List<ICtDevice>) getHibernateTemplate()
					.findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<ICtDevice> findByProperty(String propertyName, Object value) {
		log.debug("finding ICtDevice instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from ICtDevice as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<ICtDevice> findByServer(Object server) {
		return findByProperty(SERVER, server);
	}

	public List<ICtDevice> findByPort(Object port) {
		return findByProperty(PORT, port);
	}

	public List<ICtDevice> findBySn(Object sn) {
		return findByProperty(SN, sn);
	}

	public List<ICtDevice> findByManufactureId(Object manufactureId) {
		return findByProperty(MANUFACTURE_ID, manufactureId);
	}

	public List<ICtDevice> findByModelId(Object modelId) {
		return findByProperty(MODEL_ID, modelId);
	}

	public List<ICtDevice> findByOs(Object os) {
		return findByProperty(OS, os);
	}

	public List<ICtDevice> findByImei(Object imei) {
		return findByProperty(IMEI, imei);
	}

	public List<ICtDevice> findByUses(Object uses) {
		return findByProperty(USES, uses);
	}

	public List<ICtDevice> findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List<ICtDevice> findAll() {
		log.debug("finding all ICtDevice instances");
		try {
			String queryString = "from ICtDevice";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ICtDevice merge(ICtDevice detachedInstance) {
		log.debug("merging ICtDevice instance");
		try {
			ICtDevice result = (ICtDevice) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ICtDevice instance) {
		log.debug("attaching dirty ICtDevice instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ICtDevice instance) {
		log.debug("attaching clean ICtDevice instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public ICtDevice findByRunnerId(int runnerId) {
		String hql = "from ICtDevice where id=(select deviceId from ICtRunner where id=?)";
		List<ICtDevice> list = getHibernateTemplate().find(hql, runnerId);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
}