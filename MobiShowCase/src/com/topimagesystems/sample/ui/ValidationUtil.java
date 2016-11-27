package com.topimagesystems.sample.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.topimagesystems.util.StringUtils;

/**
 *
 */
public class ValidationUtil
{
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9._%+-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String POSITIVE_INT_NUMBER_PATTERN = "^(?:|0|[1-9]\\d*)?$";
	private static final String POSITIVE_DECIMAL_NUMBER_PATTERN = "^(?:|0|[1-9]\\d*)(?:\\.\\d*)?$";
	private static final String URL_PATTERN = "(?i)(?:(?:https?):\\/\\/)?(?:\\S+(?::\\S*)?@)?(?:(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\u00a1-\\uffff0-9]+-?)*[a-z\\u00a1-\\uffff0-9]+)(?:\\.(?:[a-z\\u00a1-\\uffff0-9]+-?)*[a-z\\u00a1-\\uffff0-9]+)*(?:\\.(?:[a-z\\u00a1-\\uffff]{2,})))(?::\\d{2,5})?(?:\\/[^\\s]*)?";
	
	static private Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
	static private Pattern positiveDecimalNumberPattern = Pattern.compile(POSITIVE_DECIMAL_NUMBER_PATTERN);
	static private Pattern positiveIntNumberPattern = Pattern.compile(POSITIVE_INT_NUMBER_PATTERN);
	
	static private Pattern urlPattern = Pattern.compile(URL_PATTERN);
	static private Matcher matcher;

	/**
	 * @param value
	 * @return
	 */
	public static boolean isEmptyString(String value)
	{
		return StringUtils.isEmptyOrNull(value);
	}

	/**
	 * @param email
	 * @return
	 */
	public static boolean isValidEmail(String email)
	{
		if (StringUtils.isEmptyOrNull(email))
		{
			return false;
		}
		matcher = emailPattern.matcher(email);
		return matcher.matches();
	}
	
	/**
	 * @param email
	 * @return
	 */
	public static boolean isValidUrl(String email)
	{
		if (StringUtils.isEmptyOrNull(email))
		{
			return false;
		}
		matcher = urlPattern.matcher(email);
		return matcher.matches();
	}
	/*+(bool) validateDomain:(NSString*) domain
	{
	    NSError *error = NULL;
	    NSRegularExpression *expression = [NSRegularExpression regularExpressionWithPattern:@"(?i)(?:(?:https?):\\/\\/)?(?:\\S+(?::\\S*)?@)?(?:(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\u00a1-\\uffff0-9]+-?)*[a-z\\u00a1-\\uffff0-9]+)(?:\\.(?:[a-z\\u00a1-\\uffff0-9]+-?)*[a-z\\u00a1-\\uffff0-9]+)*(?:\\.(?:[a-z\\u00a1-\\uffff]{2,})))(?::\\d{2,5})?(?:\\/[^\\s]*)?" options:NSRegularExpressionCaseInsensitive error:&error];
	    if (error)
	    {
	        NSLog(@"error");
	        return NO;
	    }
	    NSRange range = [expression rangeOfFirstMatchInString:domain options:NSMatchingCompleted range:NSMakeRange(0, [domain length])];
	    if (!NSEqualRanges(range, NSMakeRange(NSNotFound, 0))){
	        NSString *match = [domain substringWithRange:range];
	        NSLog(@"%@", match);
	        return YES;
	    }   
	    else {
	        NSLog(@"no match");
	        return NO;
	    }    
	}*/
	
	/**
	 * @param number
	 * @return
	 */
	public static boolean isPositiveDecimalNumber(String number) {
		if (StringUtils.isEmptyOrNull(number))
		{
			return false;
		}
		matcher = positiveDecimalNumberPattern.matcher(number);
		return matcher.matches();
	}
	
	/**
	 * @param number
	 * @return
	 */
	public static boolean isPositiveIntNumber(String number) {
		if (StringUtils.isEmptyOrNull(number))
		{
			return false;
		}
		matcher = positiveIntNumberPattern.matcher(number);
		return matcher.matches();
	}

}