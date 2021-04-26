package com.jdmv.springboot.backend.apirest.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jdmv.springboot.backend.apirest.models.entity.Cliente;
import com.jdmv.springboot.backend.apirest.models.entity.Region;
import com.jdmv.springboot.backend.apirest.models.services.IClienteService;
import com.jdmv.springboot.backend.apirest.models.services.IUploadFileService;

@CrossOrigin(origins = {"http://localhost:4200", "*"}) 
@RestController //Specifies the class as a REST Controller for the MVC
@RequestMapping("/api") //Generates the URL for End points
public class ClienteRestController {
	
	@Autowired  //you can inject as many component as you want. Service annotated classes count as components
	private IClienteService clienteService; //In spring when we create a bean when the typo generics he's gonna search the first candidate who's implemeting this interface if more than one exist needs to use a qualifier annotation
	
	@Autowired
	private IUploadFileService uploadService;
	
	//Logger 
	//private final Logger log = LoggerFactory.getLogger(ClienteRestController.class);
	
	
	@GetMapping("/clientes")
	public List<Cliente> index(){
		return clienteService.findAll();
		
	}
	
	@GetMapping("/clientes/page/{page}")
	public Page<Cliente> index(@PathVariable Integer page){
		return clienteService.findAll(PageRequest.of(page, 4));
		
	}
	

	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@GetMapping("/clientes/{id}") 
	public ResponseEntity<?> show(@PathVariable Long id) {   //@PathVariable to take param from uri
		
		Cliente cliente = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			cliente = clienteService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error en consulta de base de datos");
			response.put("error", e.getMessage()+": "+e.getMostSpecificCause());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);	
		}
		
		if(cliente == null) {
			response.put("mensaje", "El cliente ID: "+id.toString()+" no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/clientes")
	//@ResponseStatus(code = HttpStatus.CREATED) //if not assigned will return 200 if resolve OK
	//@RequestBody to take the param from the request body
	//@Valid annotation before processing the request body to intercept amd validate the request received
	//BindingResult holds the result of a validation and binding and contains errors that may have occurred.
	public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result) { 
		
		Cliente newCliente = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			//old way before JAVA 8 for managing validations rules defined in class entity
//			List<String> errors = new ArrayList<>();
//			
//			//Field errors returns a type "fieldError"
//			for(FieldError err: result.getFieldErrors()) {
//				errors.add("El campo '"+err.getField()+"' "+err.getDefaultMessage());
//			}
			//way of validation rules with streams 
			// first convert the stream flow into string 
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
					.collect(Collectors.toList());
					
					
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			
		}
		
		try {
			newCliente = clienteService.save(cliente);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error en insertar en la base de datos");
			response.put("error", e.getMessage()+": "+e.getMostSpecificCause());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	
		response.put("mensaje", "Cliente creado con éxito");
		response.put("cliente", newCliente);
		//cliente.setCreateAt(new Data()); to edit, add fields coming from the request this can be resolve in a elegant way using PrePersist event inside entity class
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/clientes/{id}")
	//BindingResult ALWAYS have to be after the @Valid variable annotation 
	public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		Cliente clienteUpdated = null;
		Cliente clienteActual = null;
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
					.collect(Collectors.toList());
					
					
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
	
		clienteActual = clienteService.findById(id);

		if(clienteActual == null) {
			response.put("mensaje", "Error al editar cliente ID: "+id.toString()+" no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			clienteActual.setApellido(cliente.getApellido());
			clienteActual.setNombre(cliente.getNombre());
			clienteActual.setEmail(cliente.getEmail());		
			clienteActual.setCreateAt(cliente.getCreateAt());
			clienteActual.setRegion(cliente.getRegion());
			
			clienteUpdated = clienteService.save(clienteActual);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar en la base de datos");
			response.put("error", e.getMessage()+": "+e.getMostSpecificCause());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);	
		}
		
		response.put("mensaje", "Cliente actualizado con éxito");
		response.put("cliente", clienteUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/clientes/{id}")
	//@ResponseStatus(code = HttpStatus.NO_CONTENT) //no content when nothing is returned
	//not need to validate empty or invalid id since the dao interface does for us
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();	
		
		try {
			Cliente cliente = clienteService.findById(id);
			uploadService.deleteImage(cliente.getPhoto());
			clienteService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar en la base de datos");
			response.put("error", e.getMessage()+": "+e.getMostSpecificCause());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	
		response.put("mensaje", "Cliente eliminado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK) ;
	}
	
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@PostMapping("/clientes/upload") //Using RequestParam to get a param from a formdata and no from a request body
	public ResponseEntity<?> upload(@RequestParam("image") MultipartFile image, @RequestParam("id") Long id){
		Map<String, Object> response = new HashMap<>();	
		
		Cliente cliente = clienteService.findById(id);
		
		if(!image.isEmpty()) {
//			//UUID is to specify a random identifier to the image, avoiding having the same names for images, we also delete any whitespaces
//			String fileName = UUID.randomUUID().toString() +"_"+image.getOriginalFilename().replace(" ", "");
//			//we specify the path in a variable Path type, the path string have to be in absolute uri e.g. C\\temp\\img. in this case we're saving in the root of the proyect.
//			Path filePath = Paths.get("uploads").resolve(fileName).toAbsolutePath();
//			log.info(filePath.toString());
			String imageName = null;
			try {
				//we copy the input stream into the path and assign the name
//				Files.copy(image.getInputStream(), filePath);
				imageName = uploadService.saveImage(image);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir la imagen del cliente");
				response.put("error", e.getMessage()+": "+e.getCause());
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			//we check if the user has a photo previously assigned and deleting it if exist
			String oldPhotoName = cliente.getPhoto();
			uploadService.deleteImage(oldPhotoName);
			
			cliente.setPhoto(imageName);
			clienteService.save(cliente);
			
			response.put("cliente", cliente);
			response.put("mensaje", "la iamgen: "+imageName+" ha sido subida correctamente");
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK) ;
			
		}else {
			response.put("mensaje", "Error al subir la imagen del cliente, archivo vacio");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);	
		}
	}
	
	//Handler to download the photo
	@GetMapping("/uploads/img/{photoName:.+}")
	public ResponseEntity<Resource> showPhoto(@PathVariable String photoName){ //the resource type specify a file dependency that's going to be required in the runtime.
		
		Resource rsrc = null;
		
		try {
			rsrc = uploadService.downloadImage(photoName);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		HttpHeaders head = new HttpHeaders();
		head.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+rsrc.getFilename() +"\"");
		return new ResponseEntity<Resource>(rsrc, head, HttpStatus.OK);
		
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/clientes/regiones")
	public List<Region> getRegions(){
		return clienteService.Regions();
	}
	
}
