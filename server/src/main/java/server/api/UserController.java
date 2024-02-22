/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package server.api;

import commons.SingleEntry;
import org.hibernate.Hibernate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import commons.User;
import server.database.SingleRepository;
import server.database.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * A REST API for creating and getting users
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository users;
    private final SingleRepository singleRepository;

    /**
     * The constructor for the UserController class
     * @param users An instance of the UserRepository
     * @param singleRepository
     */
    public UserController(UserRepository users, SingleRepository singleRepository) {
        this.users = users;
        this.singleRepository = singleRepository;
    }

    /**
     * A GET endpoint for getting all users
     * @return list of all users in the database
     */
    @GetMapping(path = {"", "/"})
    public List<User> getAll() {
        return users.findAll();
    }

    /**
     * A POST endpoint to save a user into the database
     * @param user a user to insert into the database
     * @return a ResponseEntity containing the user
     */
    @PostMapping(path = {"", "/"})
    public ResponseEntity<User> addUser(@RequestBody User user) {

        if (user.uuid < 0 || user.name == null || user.score != 0 || this.users.existsById(user.uuid)) {
            return ResponseEntity.badRequest().build();
        }

        User saved = users.save(user);
        return ResponseEntity.ok(saved);
    }

    /**
     * A POST endpoint to save a user into the database
     * @param user a user to insert into the database
     * @return a ResponseEntity containing the user
     */
    @PostMapping("/addSingle")
    public ResponseEntity<SingleEntry> addSingle(@RequestBody SingleEntry user) {

        if (user.uuid < 0 || user.name == null) {
            return ResponseEntity.badRequest().build();
        }

        SingleEntry saved = singleRepository.save(user);
        return ResponseEntity.ok(saved);
    }

    /**
     * A GET endpoint for getting a specific user by their uuid
     * @param uuid the uuid of the user
     * @return a ResponseEntity containing the user
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<User> getUserById(@PathVariable("uuid") Long uuid) {
        if (uuid < 0 || !users.existsById(uuid)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(users.getById(uuid));
    }

    /**
     * A GET endpoint for resetting score to zero
     * @param uuid the uuid of the user
     * @return a ResponseEntity containing the user
     */
    @GetMapping("/reset/{uuid}")
    public ResponseEntity<User> resetScore(@PathVariable("uuid") Long uuid) {
        Optional<User> user = users.findById(uuid);
        User save = user.get();
        save.setScore(0);
        User res = users.save(save);
        return ResponseEntity.ok(res);
    }

    /**
     * A GET endpoint for getting a specific user by their uuid
     * @param uuid the uuid of the user
     * @return a ResponseEntity containing the user
     */
    @GetMapping("delete/{uuid}")
    public void deleteUserById(@PathVariable("uuid") Long uuid) {
        if (uuid < 0 || !users.existsById(uuid)) {
            ResponseEntity.badRequest().build();
        }
        User left = users.getById(uuid);
        left.setSessionId(null);
        users.save(left);
    }

    /**
     * Renames and returns the user (of the given UUID) to the given String.
     *
     * @param uuid - UUID of the user to rename.
     * @param newUsername - the new username to rename the user to.
     * @return - renamed User.
     */
    @GetMapping("/{uuid}/{newUsername}")
    public ResponseEntity<User> renameUser(@PathVariable("uuid") Long uuid, @PathVariable("newUsername") String newUsername) {
        if (uuid < 0 || !users.existsById(uuid)) {
            return ResponseEntity.badRequest().build();
        }

        User tempUser = (User) Hibernate.unproxy(users.getById(uuid));
        tempUser.setName(newUsername);
        users.save(tempUser);

        return ResponseEntity.ok(tempUser);
    }

}