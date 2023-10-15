    function getAllDesigns(){
        loadPage(0);
    }
    function displayDesigns(data) {
        const designsList = document.getElementById('designs-list');
        designsList.innerHTML = '';

        if (data.length > 0) {
            data.forEach(design => {
                const row = document.createElement('tr');
                row.innerHTML = `<td>${design.designName}</td><td>${design.description}</td><td>${design.creatorName}</td>`;
                designsList.appendChild(row);
            });
        } else {
            designsList.innerHTML = '<tr><td colspan="3">No designs found.</td></tr>';
        }
    }

    // Function to generate pagination links
    function generatePagination(totalPages, currentPage) {
        const pagination = document.getElementById('pagination');
        pagination.innerHTML = '';

        // Add "Previous" button
        const prevButton = document.createElement('button');
        prevButton.textContent = 'Previous';
        prevButton.classList.add('page-link');
        prevButton.addEventListener('click', () => loadPage(currentPage - 1));
        pagination.appendChild(prevButton);

        for (let page = 0; page < totalPages; page++) {
            const pageLink = document.createElement('span');
            pageLink.textContent = page;
            pageLink.classList.add('page-link');
            pageLink.addEventListener('click', () => loadPage(page));
            pagination.appendChild(pageLink);
        }

        // Add "Next" button
        const nextButton = document.createElement('button');
        nextButton.textContent = 'Next';
        nextButton.classList.add('page-link');
        nextButton.addEventListener('click', () => loadPage(currentPage + 1));
        pagination.appendChild(nextButton);

        // Disable "Previous" button if on the first page
        if (currentPage === 0) {
            prevButton.disabled = true;
        }

        // Disable "Next" button if on the last page
        if (currentPage === totalPages - 1) {
            nextButton.disabled = true;
        }
    }

    // Function to load a specific page
    function loadPage(page) {
        // Make an API request to get designs for the specified page and size
        const pageSize = 5;
        fetch(`http://localhost:4040/api/designs?page=${page}&size=${pageSize}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }
        })
        .then(response => response.json())
        .then(data => {
            // Display the designs for the selected page
            displayDesigns(data.contents);

            // Update pagination with the current page
            generatePagination(data.totalPages, page);
        })
        .catch(error => {
            console.error('API request error:', error);
        });
    }
