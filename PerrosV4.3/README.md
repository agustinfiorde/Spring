# Login con Redes Sociales

### Configuración

Son necesarias las siguientes dependencias: 

```
		<dependency>
		    <groupId>org.springframework.boot</groupId>
		    <artifactId>spring-boot-starter-oauth2-client</artifactId>
		</dependency>
		<dependency>
		    <groupId>org.springframework.security</groupId>
		    <artifactId>spring-security-oauth2-client</artifactId>
		</dependency>
		<dependency>
		    <groupId>org.springframework.security</groupId>
		    <artifactId>spring-security-oauth2-jose</artifactId>
		</dependency>
```


### Componentes

##### LoginController

Este componente es el encargado de orquestar la autenticación con las redes sociales. 

Este método abre la vista oauth_login.html e inyecta en el modulo los metodos de login soportados y las url de autenticación. 
```
    @GetMapping("/oauth_login")
	public String getLoginPage(Model model) {

		Iterable<ClientRegistration> clientRegistrations = null;
		ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository).as(Iterable.class);
		if (type != ResolvableType.NONE && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
			clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
		}

		clientRegistrations.forEach(registration -> 
			oauth2AuthenticationUrls.put(registration.getClientName(), authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
		model.addAttribute("urls", oauth2AuthenticationUrls);

		return "oauth_login";
	}
```

Este método se llama una vez que la red social realiza la autenticación. Tomamos los datos de autenticación y le solicitamos a la red social datos de usuarios que luego insertamos en el modelo. La vista ok.html muestra los datos tomados de la red social. 

```
	@GetMapping("/loginSuccess")
	public String getLoginInfo(Model model, OAuth2AuthenticationToken authentication) {
		OAuth2AuthorizedClient client = authorizedClientService.
				loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());

		String userInfoEndpointUri = client.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();

		if (!ObjectUtils.isEmpty(userInfoEndpointUri)) {
			
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken().getTokenValue());
			
			HttpEntity<String> entity = new HttpEntity<>("", headers);
			ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
			
			Map userAttributes = response.getBody();
			model.addAttribute("name", userAttributes.get("name"));
			
		}

		return "ok";
	}
	```

##### SecurityConfig: 


