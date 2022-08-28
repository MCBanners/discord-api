package com.mcbanners.discordapi.controller;

import com.mcbanners.discordapi.models.user.DiscordUser;
import com.mcbanners.discordapi.services.DiscordUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("discord")
public class DiscordUserController {
    private final DiscordUserService discordUserService;

    @Autowired
    public DiscordUserController(DiscordUserService discordUserService) {
        this.discordUserService = discordUserService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DiscordUser> getUser(@RequestParam String id) {
        final DiscordUser user = discordUserService.getUser(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }
}
