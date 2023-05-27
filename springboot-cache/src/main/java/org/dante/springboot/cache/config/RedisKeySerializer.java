package org.dante.springboot.cache.config;

import java.nio.charset.Charset;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class RedisKeySerializer implements RedisSerializer<String> {

	private final Charset charset;

	public RedisKeySerializer() {
		this(Charset.forName("UTF8"));
	}

	public RedisKeySerializer(Charset charset) {
		Assert.notNull(charset, "字符集不允许为NULL");
		this.charset = charset;
	}

	@Override
	public byte[] serialize(String string) throws SerializationException {
			return new StringBuilder("SPIRIT:").append(string).toString().getBytes(charset);
	}

	@Override
	public String deserialize(byte[] bytes) throws SerializationException {
		return (bytes == null ? null : new String(bytes, charset));
	}
}
