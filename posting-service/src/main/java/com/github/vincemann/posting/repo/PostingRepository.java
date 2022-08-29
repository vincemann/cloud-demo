
package com.github.vincemann.posting.repo;
import com.github.vincemann.posting.model.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostingRepository extends JpaRepository<Posting,Long> {
}
	