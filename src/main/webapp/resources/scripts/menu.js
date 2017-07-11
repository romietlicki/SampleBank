$(document).ready(function() {

	/*
	 * Restore expanded menu state
	 */
	activeMenu = getUrlParameter("amenu");
	
	if(activeMenu == null) {
		$("dd:gt(0)").hide();
	} else {
		$("dd").not(":eq(" + activeMenu + ")").hide();
	}

	/*
	 * Menu behavior
	 */
	$("dt a").click(function() {

		$("dd").slideUp("medium");
		
		$(this).parent().next().slideDown("medium");
		
		activeMenu = $(this).closest("dl").index();

		return false;
	});
	
	
	/*
	 * Link behavior to restore menu state
	 */
	$("dd a").click(function() {
		if(activeMenu != null) {
			window.location.href = $(this).attr("href")
										.concat("?amenu=").concat(activeMenu);
			return false;
		}
	});
});

function getUrlParameter(sParam) {
    
	var urlQueryString = decodeURIComponent(window.location.search.substring(1));
    var sURLParametersArray = urlQueryString.split('&');

    for (var i = 0; i < sURLParametersArray.length; i++) {
        
    	var sParameterEntry = sURLParametersArray[i].split('=');

        if (sParameterEntry[0] === sParam) {
            return sParameterEntry[1] === undefined ? true : sParameterEntry[1];
        }
    }
};