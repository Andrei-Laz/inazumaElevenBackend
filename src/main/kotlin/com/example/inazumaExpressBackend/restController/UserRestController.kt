package com.example.inazumaExpressBackend.restController
//
//import com.example.inazumaExpressBackend.model.LoginRequest
//import com.example.inazumaExpressBackend.model.RegisterRequest
//import com.example.inazumaExpressBackend.service.UserService
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.http.ResponseEntity
//import org.springframework.web.bind.annotation.*
//
//@RestController
//@RequestMapping("/api/auth")
//class UserRestController {
//
//    @Autowired
//    private lateinit var userService: UserService
//
//    // Register endpoint - returns user object on success
//    @PostMapping("/register")
//    fun register(
//        @RequestBody credentials: RegisterRequest
//    ): ResponseEntity<*> {
//        val user = userService.registerUser(
//            credentials.username,
//            credentials.password,
//            credentials.email
//        )
//        return if (user != null) {
//            ResponseEntity.ok(user.copy(password = "")) // Hide password in response
//        } else {
//            ResponseEntity.badRequest().body("Username or email already exists")
//        }
//    }
//
//    // Login endpoint - returns user object (without password) on success
//    @PostMapping("/login")
//    fun login(
//        @RequestBody credentials: LoginRequest
//    ): ResponseEntity<*> {
//        val user = userService.loginUser(
//            credentials.username,
//            credentials.password
//        )
//        return if (user != null) {
//            ResponseEntity.ok(user.copy(password = "")) // CRITICAL: Never send password to client
//        } else {
//            ResponseEntity.status(401).body("Invalid credentials")
//        }
//    }
//}