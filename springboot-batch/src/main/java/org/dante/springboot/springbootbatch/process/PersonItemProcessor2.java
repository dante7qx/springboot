package org.dante.springboot.springbootbatch.process;

import org.dante.springboot.springbootbatch.po.PersonPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * 数据处理
 * 
 * @author dante
 *
 */
@Component
public class PersonItemProcessor2 implements ItemProcessor<PersonPO, PersonPO> {

	private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor2.class);
	
	@Override
	public PersonPO process(PersonPO person) throws Exception {
		final String firstName = "-> *** " + person.getFirstName() + " *** <-";
        final String lastName = "-> *** " + person.getLastName() + " *** <-";

        // 将 firstName 和 lastName 处理为大写
        final PersonPO transformedPerson = new PersonPO(firstName, lastName);

        log.info("将 ({}) 转换成 ({})", person, transformedPerson);

        return transformedPerson;
	}

}
