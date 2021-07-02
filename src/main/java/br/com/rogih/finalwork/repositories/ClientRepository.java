package br.com.rogih.finalwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rogih.finalwork.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
	
}
