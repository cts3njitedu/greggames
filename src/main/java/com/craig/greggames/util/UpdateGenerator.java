package com.craig.greggames.util;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.ReflectionUtils.MethodCallback;

import com.craig.greggames.model.game.cards.spades.SpadeNotifications;

@Service
public class UpdateGenerator {

	@Autowired
	private GregMapper gregMapper;

	private static final Set<SpadeNotifications> spadeNotifications = new HashSet<>(Arrays.asList(

			SpadeNotifications.LEAVE_GAME, SpadeNotifications.NEW_PLAYER

	));

	
	public void makeUpdate(Update update, Object object, StringBuilder fieldValue,
			SpadeNotifications playerNotification) {

		if (object == null) {
			return;
		}
		ReflectionUtils.doWithMethods(object.getClass(), new MethodCallback() {

			@Override
			public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
				// TODO Auto-generated method stub
				String name = method.getName();

				if (name.startsWith("get")) {
					// fieldValue.append
					String property = Introspector.decapitalize(name.substring(3));
					StringBuilder value = new StringBuilder().append(fieldValue).append(property);
					System.out.println(value);
					boolean isPrimitive = ClassUtils.isPrimitiveOrWrapper(method.getReturnType());
					boolean isMap = Map.class.isAssignableFrom(method.getReturnType());
					boolean isCollection = Collections.class.isAssignableFrom(method.getReturnType());
					boolean isEnum = Enum.class.isAssignableFrom(method.getReturnType());
					if (isPrimitive) {
						if ("activePlayers".equals(value.toString())) {
							if (spadeNotifications.contains(playerNotification)) {
								update.set(value.toString(), ReflectionUtils.invokeMethod(method, object));
							}
						} else {
							update.set(value.toString(), ReflectionUtils.invokeMethod(method, object));
						}

					} else if (String.class == method.getReturnType()) {
						if ("activePlayers".equals(value.toString())) {
							if (spadeNotifications.contains(playerNotification)) {
								update.set(value.toString(), ReflectionUtils.invokeMethod(method, object));
							}
						} else {
							update.set(value.toString(), ReflectionUtils.invokeMethod(method, object));
						}

					} else if (isEnum) {
						update.set(value.toString(), ReflectionUtils.invokeMethod(method, object));
					} else if (isCollection) {
						update.set(value.toString(), ReflectionUtils.invokeMethod(method, object));
					} else if (isMap) {
						Map<?, ?> map = (Map<?, ?>) ReflectionUtils.invokeMethod(method, object);
						if (map == null || map.size() == 0) {
							update.set(value.toString(), map);
						}
						for (Entry<?, ?> entry : map.entrySet()) {
							makeUpdate(update, entry.getValue(), value.append(".").append(entry.getKey()).append("."),
									playerNotification);
						}

					} else {
						Object newObject = ReflectionUtils.invokeMethod(method, object);
						if (newObject == null) {
							update.set(value.toString(), newObject);
						} else {
							makeUpdate(update, newObject, value.append("."), playerNotification);
						}

					}
				}

			}

		});

	}

	public void makeUpdateFields(Update update, Map<?, ?> map, Set<String>excludedFields, Set<String>makeEmptyIfNull) {
		
		makeUpdateFields(update, map, "",excludedFields,makeEmptyIfNull);
	
	}
	private void makeUpdateFields(Update update, Map<?, ?> map, String fieldValue,Set<String>excludedFields,Set<String>makeEmptyIfNull) {
		
		map.forEach((k, v) ->{
			
			if(v instanceof Map) {
				if(((Map<?,?>) v).size()==0) {
					if(!excludedFields.contains(fieldValue+k)) {
						update.set(fieldValue+k, v);
					}
//					System.out.println(fieldValue+k + ":"+v);
				}
				makeUpdateFields(update, (Map<?,?>) v, fieldValue+k+".",excludedFields,makeEmptyIfNull);
			}
			else {
				if(!excludedFields.contains(fieldValue+k)) {
					if(v==null) {
						if(makeEmptyIfNull.contains(fieldValue+k)) {
							update.set(fieldValue+k, new Object());
						}
						else {
							update.set(fieldValue+k, v);
						}
					}
					else {
						update.set(fieldValue+k, v);
					}
					
				}
//				System.out.println(fieldValue+k + ":" + v);
			}
		});

	}

}
