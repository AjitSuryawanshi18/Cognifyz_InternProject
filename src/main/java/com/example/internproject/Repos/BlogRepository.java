//BlogRepository.java step 2
package com.example.internproject.Repos;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.internproject.model.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {
}

