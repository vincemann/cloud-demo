
package com.github.vincemann.localposting.repo;
import com.github.vincemann.localposting.model.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostingRepository extends JpaRepository<Posting,Long> {

}
	