package org.dante.springboot.dao;

import org.dante.springboot.po.AttachmentPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentDAO extends JpaRepository<AttachmentPO, Long> {

}
