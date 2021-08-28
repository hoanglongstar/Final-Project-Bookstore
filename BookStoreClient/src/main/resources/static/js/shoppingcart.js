$(document).ready(function() {
	
	$(".btn-minus").on("click", function(event) {
		event.preventDefault();
		
		productSku = $(this).attr("pid");
		inputQuantity = $("#quantity" + productSku);
		newQuantity = parseInt(inputQuantity.val()) - 1;
		if (newQuantity > 0) {
			inputQuantity.val(newQuantity);	
		}
	});
	$(".btn-plus").on("click", function(event) {
		event.preventDefault();
		
		productSku = $(this).attr("pid");
		inputQuantity = $("#quantity" + productSku);
		newQuantity = parseInt(inputQuantity.val()) + 1;
		
		inputQuantity.val(newQuantity);	

	});
})

	