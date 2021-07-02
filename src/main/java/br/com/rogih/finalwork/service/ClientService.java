package br.com.rogih.finalwork.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rogih.finalwork.dto.ClientDTO;
import br.com.rogih.finalwork.entities.Client;
import br.com.rogih.finalwork.repositories.ClientRepository;
import br.com.rogih.finalwork.service.exceptions.DatabaseException;
import br.com.rogih.finalwork.service.exceptions.ResourceNotFoundException;

/**
 * Classe de serviço da entidade Client, deve ser usada sempre que necessário enviar dados para camada service do Client.
 * @author Higor Souza
 *
 */

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;
	
	/**
	 * Método de serviço que lista todos os registros da entidade Client.
	 * @param pageRequest
	 * @author Higor Souza
	 */
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
		Page<Client> list = clientRepository.findAll(pageRequest);
		return list.map(x -> new ClientDTO(x));
	}
	
	/**
	 * Método de serviço para listar os campos de apenas UM registro da entidade Client.
	 * @param id
	 * @author Higor Souza
	 */

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> obj = clientRepository.findById(id);
		Client entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found."));
		return new ClientDTO(entity);
	}

	/**
	 * Método para inserir dados da entidade Client.
	 * @param dto
	 * @author Higor Souza
	 */
	@Transactional(readOnly = true)
	public ClientDTO insert(ClientDTO dto) {
		Client entity = new Client();
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setBirthDate(dto.getBirthDate());
		entity.setChildren(dto.getChildren());
		entity = clientRepository.save(entity);
		return new ClientDTO(entity);
	}

	/**
	 * Método de atualização de dados da entidade Client.
	 * @param id
	 * @param dto
	 * @author Higor Souza
	 */
	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		try {
			Client entity = clientRepository.getOne(id);
			entity.setName(dto.getName());
			entity.setCpf(dto.getCpf());
			entity.setIncome(dto.getIncome());
			entity.setBirthDate(dto.getBirthDate());
			entity.setChildren(dto.getChildren());		
			entity =  clientRepository.save(entity);
			return new ClientDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found "+ id);
		}		
	}

	/**
	 * Método para exclusão de dados da entidade Client.
	 * @param id
	 * @author Higor Souza
	 */
	public void delete(Long id) {
		try {
			clientRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
}
