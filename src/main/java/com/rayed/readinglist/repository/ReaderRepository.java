package com.rayed.readinglist.repository;

import com.rayed.readinglist.domain.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rayed on 7/1/17.
 */
public interface ReaderRepository extends JpaRepository<Reader, String> {
}
