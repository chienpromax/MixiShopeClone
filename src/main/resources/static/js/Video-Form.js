
function showShareForm(videoId) {
    document.getElementById('shareForm-' + videoId).style.display = 'block';
    document.getElementById('overlay').classList.add('show');
}

function hideShareForm() {
    var forms = document.querySelectorAll('.share-form');
    forms.forEach(form => form.style.display = 'none');
    document.getElementById('overlay').classList.remove('show');
}
