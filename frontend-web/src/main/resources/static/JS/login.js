setTimeout(()=>{
        const elements = document.getElementsByClassName("loginError");
    if (elements.length>0){
        for(let i=0; i<elements.length; i++) {
            const element = elements[i];
            if(element.tagName === "INPUT"){
                element.classList.remove('border-red-500')
                element.classList.add('border-oliveira')
                //element.value= ""; -- mete o input vazio
            }
            else if (element.tagName === "SPAN"){
                element.style.display = "none";
            }
            else if(element.tagName ==="DIV"){
                element.style.display= "none";
            }
        }
    }
},3000);

function logout(event) {
    const confirmed = confirm("Do you want to end the session?")
    if(!confirmed){
        event.preventDefault();
        return false;
    }
    console.log(confirmed)
}