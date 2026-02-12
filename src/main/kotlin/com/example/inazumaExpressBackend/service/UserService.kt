package com.example.inazumaExpressBackend.service
//
//import com.example.inazumaExpressBackend.model.User
//import com.example.inazumaExpressBackend.repository.UserRepository
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.stereotype.Service
//import org.springframework.transaction.annotation.Transactional
//
//@Service
//class UserService {
//
//    @Autowired
//    private lateinit var userRepository: UserRepository
//
//    /**
//     * Register a new user (plain text password - NO HASHING)
//     * Returns null if username already exists
//     */
//    @Transactional
//    fun registerUser(username: String, password: String, email: String): User? {
//        // Check if username already exists (case-insensitive)
//        if (userRepository.findByUserName(username).isNotEmpty()) {
//            return null
//        }
//
//        // Create and save user with PLAIN TEXT password
//        val newUser = User(
//            userName = username,
//            password = password,  // ⚠️ PLAIN TEXT - FOR DEMO ONLY
//            email = email
//        )
//
//        return try {
//            userRepository.save(newUser)
//        } catch (e: Exception) {
//            // Catches duplicate email constraint violation
//            null
//        }
//    }
//
//    /**
//     * Login user (plain text password comparison - NO HASHING)
//     * Returns user object on success, null on failure
//     */
//    fun loginUser(username: String, password: String): User? {
//        val users = userRepository.findByUserName(username)
//
//        // Username not found
//        if (users.isEmpty()) {
//            return null
//        }
//
//        val user = users.first()
//
//        // Simple string comparison (NO HASHING)
//        return if (user.password == password) {
//            // ⚠️ SECURITY NOTE: In real apps, NEVER return password in response
//            // For this demo, we return full user. Frontend should ignore password field
//            user
//        } else {
//            null
//        }
//    }
//
//    /**
//     * Get user by ID (for profile display after login)
//     */
//    fun getUserById(userId: Int): User? {
//        return userRepository.findById(userId).orElse(null)
//    }
//
//    /**
//     * Check if username exists (for frontend validation)
//     */
//    fun usernameExists(username: String): Boolean {
//        return userRepository.findByUserName(username).isNotEmpty()
//    }
//}