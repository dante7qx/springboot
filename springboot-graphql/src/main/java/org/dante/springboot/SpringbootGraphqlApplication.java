package org.dante.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Graphql UI：http://localhost:8101/graphiql 
 * 
 * @author dante
 *
 */
@SpringBootApplication
public class SpringbootGraphqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootGraphqlApplication.class, args);
	}
}


/**
query {
    recentPosts(count: 10, offset: 0) {
        id
        title
        category
        author {
            id
            name
            thumbnail
        }
    }
}
*/