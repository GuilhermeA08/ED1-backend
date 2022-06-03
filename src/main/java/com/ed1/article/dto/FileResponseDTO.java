package com.ed1.article.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileResponseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String path;
	private String downloadUri;
	private String type;
	private Long size;
	
}
