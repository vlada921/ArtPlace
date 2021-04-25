$(document).ready(
    function () {
        $('#feed-line').scroll(
            function () {
                let lastPublication = $('#feed-line > div:last-of-type');
                if (isElementInView(lastPublication, false)) {
                    let newPage = loadNewPublicationsPage();
                    appendNewPublicationPage(newPage);
                }
            }
        );
    }
);

function isElementInView(element, fullyInView) {
    let pageTop = $(window).scrollTop();
    let pageBottom = pageTop + $(window).height();
    let elementTop = $(element).offset().top;
    let elementBottom = elementTop + $(element).height();

    if (fullyInView === true) {
        return ((pageTop < elementTop) && (pageBottom > elementBottom));
    } else {
        return (
            (elementTop < pageBottom && elementTop > pageTop)
            || (elementBottom < pageBottom && elementBottom > pageTop)
        );
    }
}

function loadNewPublicationsPage() {
    let nextPagePublications;
    $.ajax(
        '/feed/page',
        {
            method: 'get',
            async: false,
            data: {
                page: $('#current-page').val() + 1,
                size: $('#page-size').val(),
                sort: 'publicationDate,desc'
            },
            success: function (data, textStatus, jqXHR) {
                let pageInput = $('#current-page');
                pageInput.val(+pageInput.val() + 1);
                nextPagePublications = data;
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error(
                    'Error on requesting next publication page: '
                        + errorThrown
                        + '. Status: '
                        + textStatus
                );
            }
        }
    );
    return nextPagePublications;
}

function appendNewPublicationPage(newPage) {
    let feedLine = $('#feed-line');
    newPage.forEach(publication => {
        feedLine.append(
            `<div class="feed-line-item-container" id="${publication.id}">
                <h5>${publication.parentPublic.name}</h5>
                <h6>${publication.title}</h6>
                <p>${publication.publicationText}</p>
            </div>`
        );
        let newFeedLineContainer = $(`#${publication.id}`);
        publication.attachments.forEach(
            attachment => {
                newFeedLineContainer.append(
                    `<div class="publication-files-container">
                        <div class="publication-file">
                            <a href="/file/${attachment.id}">${attachment.name}</a>
                        </div>
                    </div>`
                );
            }
        );
    });
}
