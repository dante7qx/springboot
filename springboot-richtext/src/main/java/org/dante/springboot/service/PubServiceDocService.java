package org.dante.springboot.service;

import java.util.UUID;

import org.dante.springboot.dao.PubServiceDocDAO;
import org.dante.springboot.po.PubServiceDocPO;
import org.dante.springboot.vo.PubServiceDocVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

@Service
@Transactional(readOnly = true)
public class PubServiceDocService {

	@Autowired
	private PubServiceDocDAO pubServiceDocDAO;

	@Transactional
	public PubServiceDocVO persist(PubServiceDocVO vo) {
		return convertPoToVo(pubServiceDocDAO.save(convertVoToPo(vo)));
	}
	
	public PubServiceDocVO findPubServiceDocById(String id) {
		return convertPoToVo(pubServiceDocDAO.findById(id).orElse(null));
	}

	private PubServiceDocPO convertVoToPo(PubServiceDocVO vo) {
		PubServiceDocPO po = new PubServiceDocPO();
		BeanUtils.copyProperties(vo, po);
		if (StringUtils.isEmpty(po.getId())) {
			po.setId(UUID.randomUUID().toString().toLowerCase());
		}
		return po;
	}

	private PubServiceDocVO convertPoToVo(PubServiceDocPO po) {
		if (po == null)
			return null;
		PubServiceDocVO vo = new PubServiceDocVO();
		BeanUtils.copyProperties(po, vo);
		return vo;
	}
}
