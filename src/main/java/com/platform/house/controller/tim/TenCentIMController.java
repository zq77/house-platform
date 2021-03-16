package com.platform.house.controller.tim;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platform.house.tim.TencentIMHelper;
import com.platform.house.utils.ResponseUtil;

@RestController
@RequestMapping("/tim")
public class TenCentIMController {
	private static final Logger log = Logger.getLogger(TenCentIMController.class);

	@GetMapping(value = "/genUserSig")
	public ResponseEntity genUserSig(String identifier) {
		TencentIMHelper helper = TencentIMHelper.getTIMHelper();
		String userSig;
		try {
			userSig = helper.genUsersig(identifier);
		} catch (IOException e) {
			userSig = null;
			log.error(e.getMessage());
		}
		return ResponseUtil.success(userSig);
	}

	

}
