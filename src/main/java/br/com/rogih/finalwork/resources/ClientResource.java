package br.com.rogih.finalwork.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.rogih.finalwork.dto.ClientDTO;
import br.com.rogih.finalwork.service.ClientService;

/**
 * Classe de resource da entidade Client, nela contém as configurações das chamadas da API REST relacionada a entidade Client.
 * @author Higor Souza
 *
 */

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {

	@Autowired
	private ClientService clientService;
	
	/**
	 * Método para requisição GET que retorna todo os registros do Client com paginação.
	 * @param page
	 * @param linesPerPage
	 * @param direction
	 * @param orderBy
	 * @author Higor Souza
	 */
	@GetMapping
	public ResponseEntity<Page<ClientDTO>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy){
			
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		Page<ClientDTO> list = clientService.findAllPaged(pageRequest);
		return ResponseEntity.ok().body(list);
	}
	
	/**
	 * Método para requisição GET que retorna os registros de apenas UM Client.
	 * @param id
	 * @author Higor Souza
	 */
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ClientDTO> findById(@PathVariable Long id){
		ClientDTO dto = clientService.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	/**
	 * Método para requisição POST usado para enviar dados de persistência da entidade Client.
	 * @param dto
	 * @author Higor Souza
	 */
	@PostMapping
	public ResponseEntity<ClientDTO> insert(@RequestBody ClientDTO dto){
		dto = clientService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	/**
	 * Método para requisição PUT usado para enviar dados de alteração da entidade Client.
	 * @param id
	 * @param dto
	 * @author Higor Souza
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<ClientDTO> update(@PathVariable Long id, @RequestBody ClientDTO dto){
		dto = clientService.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	/**
	 * Método para requisição DELETE usado para enviar dados de exclusão da entidade Client.	
	 * @param id
	 * @return
	 * @author Higor Souza
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		clientService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
