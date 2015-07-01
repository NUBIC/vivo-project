<#assign fsmIcon = "${urls.images}/individual/fsm.png">
<#assign fsmUrl  = individual.getFSMFacultyProfileUrl()>

<#-- Determine whether this person is an author -->
<#assign isAuthor = p.hasVisualizationStatements(propertyGroups, "${core}relatedBy", "${core}Authorship") />

<#-- Determine whether this person is involved in any grants -->
<#assign obo_RO53 = "http://purl.obolibrary.org/obo/RO_0000053">

<#assign isInvestigator = (p.hasVisualizationStatements(propertyGroups, "${obo_RO53}", "${core}InvestigatorRole") || p.hasVisualizationStatements(propertyGroups, "${obo_RO53}", "${core}PrincipalInvestigatorRole") ||  p.hasVisualizationStatements(propertyGroups, "${obo_RO53}", "${core}CoPrincipalInvestigatorRole"))>

<#if (!isAuthor && !isInvestigator)>
    ${stylesheets.add('<link rel="stylesheet" href="${urls.base}/css/visualization/visualization.css" />')}
</#if>

<#if fsmUrl?has_content>
    <div class="collaboratorship-link-separator"></div>
        
    <div id="coauthorship_link_container" class="collaboratorship-link-container">
        <div class="collaboratorship-icon">
            <a href="${fsmUrl}" title="FSM Faculty Profile" target="_blank">
                <img src="${fsmIcon}" alt="FSM Faculty Profile" width="25px" height="25px" />
            </a>
        </div>
        <div class="collaboratorship-link">
            <a href="${fsmUrl}" title="FSM Faculty Profile" target="_blank">
                FSM Faculty Profile
            </a>
        </div>
    </div>
</#if>